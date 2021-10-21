package com.example.currencynb.api

import com.example.currencynb.model.CurrencyRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("api/exrates/rates")
    suspend fun getCurrency(
        @Query("periodicity")
        newsPage: Int = 0
    ): Response<CurrencyRates>
}