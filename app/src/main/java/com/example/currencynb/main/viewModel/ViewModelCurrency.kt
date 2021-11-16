package com.example.currencynb.main.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencynb.BaseApplication
import com.example.currencynb.data.EtCurrency
import com.example.currencynb.model.CurrencyRates
import com.example.currencynb.other.Resource
import com.example.currencynb.main.repository.MainRepository
import com.example.currencynb.model.CurrencyRatesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ViewModelCurrency @Inject constructor(private val currencyRepository: MainRepository, app: Application
) :AndroidViewModel(app) {
   private val _itemsCurrency: MutableStateFlow<Resource<CurrencyRates>> = MutableStateFlow(Resource.Loading())

    private var currencyPage = 0

    fun itemsCurrency() : StateFlow<Resource<CurrencyRates>> {
        return _itemsCurrency
    }
  
    init {
getCurrency()
    }

    private fun getCurrency() = viewModelScope.launch {
      safeBreakingNewsCall()
    }

    private suspend fun safeBreakingNewsCall() {
        _itemsCurrency.value = Resource.Loading()
        try {
            if(hasInternetConnection()) {
                val response = currencyRepository.getCurrency(currencyPage)
                _itemsCurrency.value = response
            } else {
                _itemsCurrency.value = Resource.Error("No internet connection")
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _itemsCurrency.value = Resource.Error("Network Failure")
                else -> _itemsCurrency.value = Resource.Error("Conversion Error")
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BaseApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun read() = currencyRepository.read().asLiveData()

    fun insert(items: List<EtCurrency>) = viewModelScope.launch {
         currencyRepository.insert(items)
    }
}