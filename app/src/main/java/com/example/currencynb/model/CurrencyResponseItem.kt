package com.example.currencynb.model

data class CurrencyResponseItem(
    val Cur_Abbreviation: String,
    val Cur_Code: String,
    val Cur_DateEnd: String,
    val Cur_DateStart: String,
    val Cur_ID: Int,
    val Cur_Name: String,
    val Cur_NameMulti: String,
    val Cur_Name_Bel: String,
    val Cur_Name_BelMulti: String,
    val Cur_Name_Eng: String,
    val Cur_Name_EngMulti: String,
    val Cur_ParentID: Int,
    val Cur_Periodicity: Int,
    val Cur_QuotName: String,
    val Cur_QuotName_Bel: String,
    val Cur_QuotName_Eng: String,
    val Cur_Scale: Int
)