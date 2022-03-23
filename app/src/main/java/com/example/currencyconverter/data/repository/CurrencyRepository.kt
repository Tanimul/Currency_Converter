package com.example.currencyconverter.data.repository

import android.util.Log
import com.example.currencyconverter.data.interfaces.ApiInterface
import com.example.currencyconverter.data.model.CurrencyItem
import com.example.currencyconverter.network.ApiClient
import retrofit2.Response

class CurrencyRepository {
    private val TAG: String = "CurrencyRep: Online"
    private val apiInterface: ApiInterface = ApiClient.getClient()

    suspend fun getExchangeList(appInfo: String): Response<CurrencyItem> {
        Log.d(TAG, "getList_Coroutine: " + apiInterface.getExchangeRate(appInfo).body())
        return apiInterface.getExchangeRate(appInfo)
    }

}