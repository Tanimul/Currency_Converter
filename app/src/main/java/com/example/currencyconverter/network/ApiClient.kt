package com.example.currencyconverter.network

import android.util.Log
import com.example.currencyconverter.data.interfaces.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        var retrofit: Retrofit? = null
        lateinit var apiInterface: ApiInterface
        private const val BASEURL = "https://openexchangerates.org/api/"
        fun getClient(): ApiInterface {
            if (retrofit == null) {
                synchronized(this) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    apiInterface = retrofit!!.create(ApiInterface::class.java)
                }

            }
            return apiInterface
        }
    }
}