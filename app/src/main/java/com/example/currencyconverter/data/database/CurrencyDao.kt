package com.example.currencyconverter.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.model.CurrencyListItem

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyList(currencyConverterItem: CurrencyListItem) //Add single Currency

    @Query("SELECT * FROM currency_table ORDER BY id ASC")
    suspend fun showingCurrencyList(): LiveData<CurrencyListItem> //showing Currency
}