package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency_table")
data class CurrencyListItem(
    var timestamp: Long,
    var rates: Rates
) : Serializable {
    @PrimaryKey
    var id: Int = 0
}