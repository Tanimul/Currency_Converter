package com.example.currencyconverter

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.databinding.ActivityHomeBinding
import com.example.currencyconverter.utils.toast
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class HomeActivity : AppBaseActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var students: Rates
    private val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    private lateinit var toDaysCalendar: Calendar
    private var dayOfToDay: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = "Currency Converter"

        toDaysCalendar = Calendar.getInstance()
        dayOfToDay = toDaysCalendar.timeInMillis


        currencyViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]


        //showAsList()
        observeList()

        binding.btnConvert.setOnClickListener {
            if (!TextUtils.isEmpty(binding.tvAmountValue.text.toString())) {
                totalExchangeRate(binding.tvAmountValue.text.toString().toInt())
            }

        }
    }

    private fun observeList() {
        currencyViewModel.currencyListItem.observe(this) {
            if (it != null) {
                showProgress(false)
                Log.d(TAG, "observeList: " + dateFormat.format(dayOfToDay))
                Log.d(TAG, "observeList: " + dateFormat.format(it.timestamp))
                if (dateFormat.format(dayOfToDay) != dateFormat.format(it.timestamp)) {
                    showProgress(true)
                    showAsList()
                    Log.d(TAG, "get Todays Exchange Rate")
                } else {
                    Log.d(
                        TAG, "Data Observed:  -> ${it.rates}"

                    )
                    val rates = Gson().fromJson(it.rates, Rates::class.java)
                    Log.d(TAG, "observeList: " + rates.BDT)
                    Log.d(TAG, "observeList: " + it.id)
                }

            } else {
                showProgress(true)
                showAsList()
                Log.d(TAG, "observeList: Null")
            }

        }
    }

    private fun showAsList() {
        val appInfo: String = "a593611b88c9400093af1c8807fbfbab"

        currencyViewModel.getExchangeList(appInfo)
        Log.d(TAG, "showAsList: ")

    }

    private fun totalExchangeRate(amount: Int) {
        Log.d(TAG, "totalExchangeRate: $amount")

    }
}