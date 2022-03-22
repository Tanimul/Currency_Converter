package com.example.currencyconverter.data.interfaces

import com.example.currencyconverter.data.model.CurrencyItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("latest.json?app_id={app_id}")
    suspend fun getExchangeRate(@Path("app_id") app_id: String): Response<CurrencyItem>

}