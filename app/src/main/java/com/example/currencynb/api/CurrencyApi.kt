package com.example.currencynb.api

import com.example.currencynb.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("api/exrates/currencies")
    suspend fun getCurrency(
        @Query("page")
        newsPage: Int = 1
    ): Response<CurrencyResponse>
}