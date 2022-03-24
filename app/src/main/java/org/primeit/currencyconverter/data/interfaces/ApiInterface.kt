package org.primeit.currencyconverter.data.interfaces

import org.primeit.currencyconverter.data.model.CurrencyItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("latest.json")
    suspend fun getExchangeRate(@Query("app_id") app_id: String): Response<CurrencyItem>

}