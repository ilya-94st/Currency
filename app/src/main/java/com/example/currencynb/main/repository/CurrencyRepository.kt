package com.example.currencynb.main.repository

import com.example.currencynb.api.CurrencyApi
import com.example.currencynb.model.CurrencyRates
import com.example.currencynb.other.Resource
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
private val api: CurrencyApi
) : MainRepository {
    override suspend fun getCurrency(base: Int): Resource<CurrencyRates> {
        return try {
            val response = api.getCurrency(base)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}
