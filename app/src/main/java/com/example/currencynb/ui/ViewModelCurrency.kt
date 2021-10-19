package com.example.currencynb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencynb.model.CurrencyResponse
import com.example.currencynb.other.Resource
import com.example.currencynb.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelCurrency @Inject constructor(private val currencyRepository: CurrencyRepository
) :ViewModel() {
   private val _itemsCurrency: MutableLiveData<Resource<CurrencyResponse>> = MutableLiveData()

    var currencyPage = 1

    fun itemsCurrency() : LiveData<Resource<CurrencyResponse>> {
        return _itemsCurrency
    }

    init {
getCurrency()
    }

    private fun getCurrency() = viewModelScope.launch {
        _itemsCurrency.postValue(Resource.Loading())
        val response = currencyRepository.getCurrency(currencyPage)
        _itemsCurrency.postValue(handleCurrency(response))
    }

    private fun handleCurrency(response: Response<CurrencyResponse>) : Resource<CurrencyResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                    resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return  Resource.Error(response.message())
    }
}