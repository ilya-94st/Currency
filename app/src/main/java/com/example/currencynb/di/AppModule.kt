package com.example.currencynb.di

import com.example.currencynb.api.CurrencyApi
import com.example.currencynb.other.Constants
import com.example.currencynb.main.repository.CurrencyRepository
import com.example.currencynb.main.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(api: CurrencyApi): MainRepository = CurrencyRepository(api)

}