package com.example.currencynb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencynb.model.CurrencyRatesItem

@Database(
    entities = [CurrencyRatesItem::class],
    version = 1, exportSchema = false
)

abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun getDaoCurrency(): DaoCurrency

}