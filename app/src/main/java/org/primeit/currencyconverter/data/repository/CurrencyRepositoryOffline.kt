package org.primeit.currencyconverter.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import org.primeit.currencyconverter.data.database.CurrencyDao
import org.primeit.currencyconverter.data.model.CurrencyList

class CurrencyRepositoryOffline(private val currencyDao: CurrencyDao) {
    private val TAG: String = "CurrencyListRep: Online"
    var list: LiveData<CurrencyList> = currencyDao.showingCurrencyList()

    suspend fun addCurrencyList(currencyListItem: CurrencyList) {
        currencyDao.addCurrencyList(currencyListItem)
        Log.d(TAG, "addSingleNote: ")
    }

}