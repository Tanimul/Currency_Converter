package org.primeit.currencyconverter.ui


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import org.primeit.currencyconverter.AppBaseActivity
import org.primeit.currencyconverter.R
import org.primeit.currencyconverter.data.model.Rates
import org.primeit.currencyconverter.databinding.ActivityHomeBinding
import org.primeit.currencyconverter.viewmodel.CurrencyViewModel
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppBaseActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var currencyViewModel: CurrencyViewModel
    private val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    private lateinit var toDaysCalendar: Calendar
    private var dayOfToDay: Long = 0L
    private var exchangeRate: Double? = 0.0
    private lateinit var rates: Rates
    private lateinit var dialog: Dialog
    private val currencyArrayList =
        arrayOf(
            "AED",
            "AFN",
            "ALL",
            "AMD",
            "ANG",
            "AOA",
            "ARS",
            "AUD",
            "AWG",
            "AZN",
            "BAM",
            "BBD",
            "BDT",
            "BGN",
            "BHD",
            "BIF",
            "BMD",
            "BND",
            "BOB",
            "BRL",
            "BSD",
            "BTC",
            "BTN",
            "BWP",
            "BYN",
            "BZD",
            "CAD",
            "CDF",
            "CHF",
            "CLF",
            "CLP",
            "CNH",
            "CNY",
            "COP",
            "CRC",
            "CUC",
            "CUP",
            "CVE",
            "CZK",
            "DJF",
            "DKK",
            "DOP",
            "DZD",
            "EGP",
            "ERN",
            "ETB",
            "EUR",
            "FJD",
            "FKP",
            "GBP",
            "GEL",
            "GGP",
            "GHS",
            "GIP",
            "GMD",
            "GNF",
            "GTQ",
            "GYD",
            "HKD",
            "HNL",
            "HRK",
            "HTG",
            "HUF",
            "IDR",
            "ILS",
            "IMP",
            "INR",
            "IQD",
            "IRR",
            "ISK",
            "JEP",
            "JMD",
            "JOD",
            "JPY",
            "KES",
            "KGS",
            "KHR",
            "KMF",
            "KPW",
            "KRW",
            "KWD",
            "KYD",
            "KZT",
            "LAK",
            "LBP",
            "LKR",
            "LRD",
            "LSL",
            "LYD",
            "MAD",
            "MDL",
            "MGA",
            "MKD",
            "MMK",
            "MNT",
            "MOP",
            "MRU",
            "MUR",
            "MVR",
            "MWK",
            "MXN",
            "MYR",
            "MZN",
            "NAD",
            "NGN",
            "NIO",
            "NOK",
            "NPR",
            "NZD",
            "OMR",
            "PAB",
            "PEN",
            "PGK",
            "PHP",
            "PKR",
            "PLN",
            "PYG",
            "QAR",
            "RON",
            "RSD",
            "RUB",
            "RWF",
            "SAR",
            "SBD",
            "SCR",
            "SDG",
            "SEK",
            "SGD",
            "SHP",
            "SLL",
            "SOS",
            "SRD",
            "SSP",
            "STD",
            "STN",
            "SVC",
            "SYP",
            "SZL",
            "THB",
            "TJS",
            "TMT",
            "TND",
            "TOP",
            "TRY",
            "TTD",
            "TWD",
            "TZS",
            "UAH",
            "UGX",
            "USD",
            "UYU",
            "UZS",
            "VES",
            "VUV",
            "WST",
            "XAF",
            "XAG",
            "XAU",
            "XCD",
            "XDR",
            "XOF",
            "XPD",
            "XPF",
            "XPT",
            "YER",
            "ZAR",
            "ZMW",
            "ZWL"
        )
    private lateinit var currencyList: ListView
    private lateinit var currencySearch: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CurrencyConverter)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = "Currency Converter"

        toDaysCalendar = Calendar.getInstance()
        dayOfToDay = toDaysCalendar.timeInMillis

        dialog = Dialog(this, R.style.MyAlertDialogTheme)
        dialog.setContentView(R.layout.dialog_searchable_spinner)
        currencyList = dialog.findViewById<ListView>(R.id.lv_currencyList)
        currencySearch = dialog.findViewById<EditText>(R.id.et_currency)

        currencyViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]

        observeList()


        binding.btnConvert.setOnClickListener {
            if (!(TextUtils.isEmpty(binding.tvAmountValue.text.toString())
                        || binding.tvSelectFrom.text.toString().isEmpty()
                        || binding.tvSelectTo.text.toString().isEmpty()
                        )
            ) {
                totalExchangeRate(
                    binding.tvAmountValue.text.toString().toDouble(),
                    binding.tvSelectFrom.text.toString(),
                    binding.tvSelectTo.text.toString(),
                )
            }

        }

        binding.tvSelectFrom.setOnClickListener {
            initSearchDialogFrom()
        }

        binding.tvSelectTo.setOnClickListener {
            initSearchDialogTo()
        }

    }

    private fun initSearchDialogFrom() {
        currencySearch.text.clear()
        currencySearch.hint = resources.getString(R.string.search)
        // show dialog
        dialog.show()
        currencyList.setOnItemClickListener { parent, _, position, _ ->
            binding.tvSelectFrom.text = parent.getItemAtPosition(position).toString()
            dialog.cancel()

        }
        val spinnerArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currencyArrayList)
        currencyList.adapter = spinnerArrayAdapter
        currencySearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                spinnerArrayAdapter.filter.filter(s)
                Log.d(TAG, "onTextChanged: $s")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }


            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun initSearchDialogTo() {
        currencySearch.text.clear()
        currencySearch.hint = resources.getString(R.string.search)
        // show dialog
        dialog.show()
        currencyList.setOnItemClickListener { parent, _, position, _ ->
            binding.tvSelectTo.text = parent.getItemAtPosition(position).toString()
            dialog.cancel()

        }
        val spinnerArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currencyArrayList)
        currencyList.adapter = spinnerArrayAdapter
        currencySearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                spinnerArrayAdapter.filter.filter(s)
                Log.d(TAG, "onTextChanged: $s")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }


            override fun afterTextChanged(p0: Editable?) {
            }

        })
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
                    Log.d(TAG, "get Today's Exchange Rate")
                } else {
                    Log.d(
                        TAG, "Data Observed:  -> ${it.rates}"
                    )
                    rates =
                        Gson().fromJson(
                            currencyViewModel.currencyListItem.value?.rates,
                            Rates::class.java
                        )
                }

            } else {
                showProgress(true)
                showAsList()
                Log.d(TAG, "observeList: Null")
            }

        }
    }


    private fun showAsList() {
        val appInfo = "a593611b88c9400093af1c8807fbfbab"

        currencyViewModel.getExchangeList(appInfo)
        Log.d(TAG, "showAsList: ")

    }

    private fun totalExchangeRate(amount: Double?, fromString: String, toString: String) {

        Log.d(TAG, "totalExchangeRate: $amount -> $fromString -> $toString")
        val fromValue: Double? = getRateForCurrency(fromString, rates)
        val toValue: Double? = getRateForCurrency(toString, rates)
        exchangeRate = ((amount?.div(fromValue!!))?.times(toValue!!))
        binding.tvExchangeRateValue.text = String.format("%.3f", exchangeRate).toDouble().toString()
        Log.d(TAG, "totalExchangeRate: $fromValue")
        Log.d(TAG, "totalExchangeRate: $toValue")
        Log.d(TAG, "totalExchangeRate: $exchangeRate")

    }

    private fun getRateForCurrency(currency: String, rates: Rates): Double? = when (currency) {

        "AED" -> rates.AED
        "AFN" -> rates.AFN
        "ALL" -> rates.ALL
        "AMD" -> rates.AMD
        "ANG" -> rates.ANG
        "AOA" -> rates.AOA
        "ARS" -> rates.ARS
        "AUD" -> rates.AUD
        "AWG" -> rates.AWG
        "AZN" -> rates.AZN
        "BAM" -> rates.BAM
        "BBD" -> rates.BBD?.toDouble()
        "BDT" -> rates.BDT
        "BGN" -> rates.BGN
        "BHD" -> rates.BHD
        "BIF" -> rates.BIF
        "BMD" -> rates.BMD?.toDouble()
        "BND" -> rates.BND
        "BOB" -> rates.BOB
        "BRL" -> rates.BRL
        "BSD" -> rates.BSD?.toDouble()
        "BTC" -> rates.BTC

        "BTN" -> rates.BTN
        "BWP" -> rates.BWP
        "BYN" -> rates.BYN
        "BZD" -> rates.BZD
        "CAD" -> rates.CAD
        "CDF" -> rates.CDF
        "CHF" -> rates.CHF
        "CLF" -> rates.CLF
        "CLP" -> rates.CLP
        "CNH" -> rates.CNH
        "CNY" -> rates.CNY
        "COP" -> rates.COP
        "CRC" -> rates.CRC
        "CUC" -> rates.CUC?.toDouble()
        "CUP" -> rates.CUP
        "CVE" -> rates.CVE
        "CZK" -> rates.CZK
        "DJF" -> rates.DJF
        "DKK" -> rates.DKK
        "DOP" -> rates.DOP
        "DZD" -> rates.DZD
        "EGP" -> rates.EGP
        "ERN" -> rates.ERN
        "ETB" -> rates.ETB
        "EUR" -> rates.EUR
        "FJD" -> rates.FJD
        "FKP" -> rates.FKP
        "GBP" -> rates.GBP
        "GEL" -> rates.GEL
        "GGP" -> rates.GGP
        "GHS" -> rates.GHS
        "GIP" -> rates.GIP
        "GMD" -> rates.GMD
        "GNF" -> rates.GNF
        "GTQ" -> rates.GTQ
        "GYD" -> rates.GYD
        "HKD" -> rates.HKD
        "HNL" -> rates.HNL
        "HRK" -> rates.HRK
        "HTG" -> rates.HTG
        "HUF" -> rates.HUF
        "IDR" -> rates.IDR
        "ILS" -> rates.ILS
        "IMP" -> rates.IMP
        "INR" -> rates.INR
        "IQD" -> rates.IQD
        "IRR" -> rates.IRR?.toDouble()
        "ISK" -> rates.ISK
        "JEP" -> rates.JEP
        "JMD" -> rates.JMD
        "JOD" -> rates.JOD
        "JPY" -> rates.JPY
        "KES" -> rates.KES
        "KGS" -> rates.KGS
        "KHR" -> rates.KHR
        "KMF" -> rates.KMF
        "KPW" -> rates.KPW?.toDouble()
        "KRW" -> rates.KRW
        "KWD" -> rates.KWD
        "KYD" -> rates.KYD
        "KZT" -> rates.KZT
        "LAK" -> rates.LAK
        "LBP" -> rates.LBP
        "LKR" -> rates.LKR
        "LRD" -> rates.LRD
        "LSL" -> rates.LSL
        "LYD" -> rates.LYD
        "MAD" -> rates.MAD
        "MDL" -> rates.MDL
        "MGA" -> rates.MGA
        "MKD" -> rates.MKD
        "MMK" -> rates.MMK
        "MNT" -> rates.MNT
        "MOP" -> rates.MOP
        "MRU" -> rates.MRU
        "MUR" -> rates.MUR
        "MVR" -> rates.MVR
        "MWK" -> rates.MWK
        "MXN" -> rates.MXN
        "MYR" -> rates.MYR
        "MZN" -> rates.MZN
        "NAD" -> rates.NAD
        "NGN" -> rates.NGN
        "NIO" -> rates.NIO
        "NOK" -> rates.NOK
        "NPR" -> rates.NPR
        "NZD" -> rates.NZD
        "OMR" -> rates.OMR
        "PAB" -> rates.PAB?.toDouble()
        "PEN" -> rates.PEN
        "PGK" -> rates.PGK
        "PHP" -> rates.PHP
        "PKR" -> rates.PKR
        "PLN" -> rates.PLN
        "PYG" -> rates.PYG
        "QAR" -> rates.QAR
        "RON" -> rates.RON
        "RSD" -> rates.RSD
        "RUB" -> rates.RUB
        "RWF" -> rates.RWF
        "SAR" -> rates.SAR
        "SBD" -> rates.SBD
        "SCR" -> rates.SCR
        "SDG" -> rates.SDG
        "SEK" -> rates.SEK
        "SGD" -> rates.SGD
        "SHP" -> rates.SHP
        "SLL" -> rates.SLL
        "SOS" -> rates.SOS
        "SRD" -> rates.SRD
        "SSP" -> rates.SSP
        "STD" -> rates.STD
        "STN" -> rates.STN
        "SVC" -> rates.SVC
        "SYP" -> rates.SYP?.toDouble()
        "SZL" -> rates.SZL
        "THB" -> rates.THB
        "TJS" -> rates.TJS
        "TMT" -> rates.TMT
        "TND" -> rates.TND
        "TOP" -> rates.TOP
        "TRY" -> rates.TRY
        "TTD" -> rates.TTD
        "TWD" -> rates.TWD
        "TZS" -> rates.TZS?.toDouble()
        "UAH" -> rates.UAH
        "UGX" -> rates.UGX
        "USD" -> rates.USD?.toDouble()
        "UYU" -> rates.UYU
        "UZS" -> rates.UZS
        "VES" -> rates.VES
        "VND" -> rates.VND
        "VUV" -> rates.VUV
        "WST" -> rates.WST
        "XAF" -> rates.XAF
        "XAG" -> rates.XAG
        "XAU" -> rates.XAU
        "XCD" -> rates.XCD
        "XDR" -> rates.XDR
        "XOF" -> rates.XOF
        "XPD" -> rates.XPD
        "XPF" -> rates.XPF
        "XPT" -> rates.XPT
        "YER" -> rates.YER
        "ZAR" -> rates.ZAR
        "ZMW" -> rates.ZMW
        "ZWL" -> rates.ZWL?.toDouble()
        else -> 0.0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.nav_about -> {
                startActivity(Intent(this, AboutAppActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}