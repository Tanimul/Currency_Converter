package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val repository: CurrencyRepository = CurrencyRepository()
    val allExchangeList = MutableLiveData<Rates>()

    fun getExchangeList(appInfo: String) {
        viewModelScope.launch(Dispatchers.IO) {

            if (repository.getExchangeList(appInfo).body()?.rates != null) {
                Log.d("fabniwfire", "getListCoroutine: " + repository.getExchangeList(appInfo).body())
                allExchangeList.postValue(repository.getExchangeList(appInfo).body()?.rates)
            }

        }
    }
}