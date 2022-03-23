package com.example.currencyconverter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.model.CurrencyList

class CurrencyRepositoryOffline(private val currencyDao: CurrencyDao) {
    private val TAG: String = "CurrencyListRep: Online"
    var list: LiveData<CurrencyList> = currencyDao.showingCurrencyList()

    suspend fun addCurrencyList(currencyListItem: CurrencyList) {
        currencyDao.addCurrencyList(currencyListItem)
        Log.d(TAG, "addSingleNote: ")
    }

}