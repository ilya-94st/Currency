package com.example.currencynb.main.repository

import com.example.currencynb.model.CurrencyRates
import com.example.currencynb.other.Resource

interface MainRepository {
    suspend fun getCurrency(base: Int): Resource<CurrencyRates>
}