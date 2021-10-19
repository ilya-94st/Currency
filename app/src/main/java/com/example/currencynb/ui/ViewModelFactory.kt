package com.example.currencynb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencynb.repository.CurrencyRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (val currencyRepository: CurrencyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelCurrency(currencyRepository) as T
    }
}