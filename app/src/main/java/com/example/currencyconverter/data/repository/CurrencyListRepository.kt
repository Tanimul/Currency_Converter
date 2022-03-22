package com.example.currencyconverter.data.repository

import android.util.Log
import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.model.CurrencyListItem

class CurrencyListRepository(private val currencyDao: CurrencyDao) {
    private val TAG: String = "CurrencyListRep: Online"

    suspend fun addCurrencyList(currencyListItem: CurrencyListItem) {
        currencyDao.addCurrencyList(currencyListItem)
        Log.d(TAG, "addSingleNote: ")
    }

}