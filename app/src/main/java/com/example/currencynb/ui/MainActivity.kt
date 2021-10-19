package com.example.currencynb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.currencynb.databinding.ActivityMainBinding
import com.example.currencynb.repository.CurrencyRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModelCurrency: ViewModelCurrency
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currencyRepository = CurrencyRepository()
        val viewModelFactory = ViewModelFactory(currencyRepository)
        viewModelCurrency = ViewModelProvider(this, viewModelFactory).get(ViewModelCurrency::class.java)
    }
}