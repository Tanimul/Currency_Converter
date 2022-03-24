package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency_table")
data class CurrencyList(
    @PrimaryKey
    var id: Int = 101,
    var timestamp: Long,
    var rates: String
) : Serializable