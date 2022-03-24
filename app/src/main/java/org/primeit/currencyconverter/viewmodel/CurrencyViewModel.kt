package org.primeit.currencyconverter.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import org.primeit.currencyconverter.data.database.CurrencyDatabase
import org.primeit.currencyconverter.data.model.CurrencyList
import org.primeit.currencyconverter.data.model.Rates
import org.primeit.currencyconverter.data.repository.CurrencyRepository
import org.primeit.currencyconverter.data.repository.CurrencyRepositoryOffline
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CurrencyRepository = CurrencyRepository()
    val allExchangeList = MutableLiveData<Rates?>()
    val repositoryStoreOffline: CurrencyRepositoryOffline
    var currencyListItem: LiveData<CurrencyList>

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()
        repositoryStoreOffline = CurrencyRepositoryOffline(currencyDao)
        currencyListItem = repositoryStoreOffline.list
    }

    private fun addCurrencyList(currencyListItem: CurrencyList) =
        viewModelScope.launch(Dispatchers.IO) {
            repositoryStoreOffline.addCurrencyList(currencyListItem)
        }

    fun getExchangeList(appInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {

            if (repository.getExchangeList(appInfo).body()?.rates != null) {
                Log.d(
                    "TAG",
                    "getListCoroutine: " + repository.getExchangeList(appInfo).body()
                )
                allExchangeList.postValue(repository.getExchangeList(appInfo).body()?.rates)
                val currencyList = CurrencyList(
                    101,
                    System.currentTimeMillis(),
                    Gson().toJson(repository.getExchangeList(appInfo).body()?.rates)
                )

                addCurrencyList(currencyList)

            }

        }
    }


}