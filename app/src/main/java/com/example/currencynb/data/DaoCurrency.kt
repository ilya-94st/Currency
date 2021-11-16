package com.example.currencynb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencynb.model.CurrencyRatesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoCurrency {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<EtCurrency>)

    @Query("select * from currency")
    fun read(): Flow<List<EtCurrency>>

}