package com.example.currencynb.repository

import com.example.currencynb.api.RetrofitInstance
import javax.inject.Inject

class CurrencyRepository @Inject constructor(

) {
    suspend fun getCurrency(page: Int) = RetrofitInstance.api.getCurrency(page)
}
