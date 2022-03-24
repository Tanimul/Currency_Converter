package org.primeit.currencyconverter.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.primeit.currencyconverter.data.model.CurrencyList

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyList(currencyList: CurrencyList) //Add single Currency

    @Query("SELECT * FROM currency_table")
    fun showingCurrencyList(): LiveData<CurrencyList> //showing Currency
}