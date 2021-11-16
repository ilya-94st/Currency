package com.example.currencynb.main.repository

import com.example.currencynb.data.EtCurrency
import com.example.currencynb.model.CurrencyRates
import com.example.currencynb.model.CurrencyRatesItem
import com.example.currencynb.other.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getCurrency(base: Int): Resource<CurrencyRates>

    suspend fun insert(items: List<EtCurrency>)

    fun read(): Flow<List<EtCurrency>>
}