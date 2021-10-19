package com.example.currencynb.repository

import com.example.currencynb.api.CurrencyApi
import com.example.currencynb.api.RetrofitInstanse
import com.example.currencynb.model.CurrencyResponse
import com.example.currencynb.other.Resource
import javax.inject.Inject

class CurrencyRepository @Inject constructor(

) {
    suspend fun getCurrency(page: Int) = RetrofitInstanse.api.getCurrency(page)
}
