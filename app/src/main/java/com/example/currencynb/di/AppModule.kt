package com.example.currencynb.di

import android.content.Context
import androidx.room.Room
import com.example.currencynb.adapter.Adapter
import com.example.currencynb.api.CurrencyApi
import com.example.currencynb.data.CurrencyDatabase
import com.example.currencynb.data.DaoCurrency
import com.example.currencynb.other.Constants
import com.example.currencynb.main.repository.CurrencyRepository
import com.example.currencynb.main.repository.MainRepository
import com.example.currencynb.other.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideMainRepository(api: CurrencyApi, dao: DaoCurrency): MainRepository = CurrencyRepository(api, dao)



    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, CurrencyDatabase::class.java, DB_NAME).build()

    @Provides
    @Singleton
    fun getCurrencyDao(db: CurrencyDatabase) = db.getDaoCurrency()

    @Provides
    @Singleton
    fun provideAdapter() = Adapter()
}