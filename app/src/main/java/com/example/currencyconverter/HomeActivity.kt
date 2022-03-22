package com.example.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.databinding.ActivityHomeBinding
import com.example.currencyconverter.viewmodel.CurrencyViewModel

class HomeActivity : AppBaseActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var students: Rates
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = "Currency Converter"

        currencyViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]


        showAsList()

        binding.btnConvert.setOnClickListener {
            if (!TextUtils.isEmpty(binding.tvAmountValue.text.toString())) {
                totalExchangeRate(binding.tvAmountValue.text.toString().toInt())
            }

        }
    }

    private fun showAsList() {
        val appInfo: String = "a593611b88c9400093af1c8807fbfbab"
        currencyViewModel.getExchangeList(appInfo)
        Log.d(TAG, "showAsList: ")
        currencyViewModel.allExchangeList.observe(this) {
            Log.d(
                TAG, "Data Observed: $it -> " + it.BDT
            )
        }
    }

    private fun totalExchangeRate(amount: Int) {
        Log.d(TAG, "totalExchangeRate: $amount")

    }
}