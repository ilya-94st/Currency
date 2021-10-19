package com.example.currencynb.repository

import com.example.currencynb.api.RetrofitInstanse

class CurrencyRepository() {
    suspend fun getCurrency(page: Int) = RetrofitInstanse.api.getCurrency(page)
}