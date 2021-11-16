package com.example.currencynb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyRatesItem(
    val Cur_Abbreviation: String,
    @PrimaryKey(autoGenerate = true)
    val Cur_ID: Int,
    val Cur_Name: String,
    val Cur_OfficialRate: Double,
    val Cur_Scale: Int,
    val Date: String
)