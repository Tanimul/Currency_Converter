package com.example.currencyconverter.data.model

data class CurrencyItem(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Rates,
    val timestamp: Int
)