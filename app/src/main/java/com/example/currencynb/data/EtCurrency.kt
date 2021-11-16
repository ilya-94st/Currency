package com.example.currencynb.data

import androidx.room.Entity

@Entity(tableName = "et")
data class EtCurrency(
    val id: Int,
    val position: Int,
    val Cur_Abbreviation: String,
    val Cur_OfficialRate: Double,
    val Cur_Name: String
)