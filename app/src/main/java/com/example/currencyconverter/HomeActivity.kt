package com.example.currencyconverter


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.data.model.Rates
import com.example.currencyconverter.databinding.ActivityHomeBinding
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*



class HomeActivity : AppBaseActivity() {
    private val TAG = "HomeActivity"
    private lateinit var binding: ActivityHomeBinding
    private lateinit var currencyViewModel: CurrencyViewModel
   /// private lateinit var students: Rates
    private val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    private lateinit var toDaysCalendar: Calendar
    private var dayOfToDay: Long = 0L
//    val hMap: HashMap<String, String> = HashMap<String, String>()
//    val listCountryName: MutableList<String> = mutableListOf()
    private var exchangeRate: Double? = 0.0
    private lateinit var rates: Rates
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CurrencyConverter)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = "Currency Converter"

        toDaysCalendar = Calendar.getInstance()
        dayOfToDay = toDaysCalendar.timeInMillis


        currencyViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]


        //  Log.d("locale", Currency.getInstance("BDT").displayName)
        Log.d("locale", Currency.getAvailableCurrencies().toString())

        // setCountryInfo()
        //showAsList()
        observeList()

        binding.btnConvert.setOnClickListener {
            if (!TextUtils.isEmpty(binding.tvAmountValue.text.toString())) {
                totalExchangeRate(
                    binding.tvAmountValue.text.toString().toDouble(),
                    binding.spFromCurrency.selectedItem.toString(),
                    binding.spToCurrency.selectedItem.toString(),
                )
            }

        }
    }

//    private fun setCountryInfo() {
////        hMap["AED"] = "Ajay"
////        hMap["AFN"] = "Ajay"
////        hMap["ALL"] = "Ajay"
////        hMap["AMD"] = "Ajay"
////        hMap["ANG"] = "Ajay"
////        hMap["AOA"] = "Ajay"
////        hMap["ARS"] = "Ajay"
////        hMap["AUD"] = "Ajay"
////        hMap["AWG"] = "Ajay"
////        hMap["AZN"] = "Ajay"
////        hMap["BAM"] = "Ajay"
////        hMap["BBD"] = "Ajay"
//        //       hMap["BDT"] = "BD"
////        hMap["BGN"] = "Ajay"
////        hMap["BHD"] = "Ajay"
////        hMap["BIF"] = "Ajay"
////        hMap["BMD"] = "Ajay"
////        hMap["BND"] = "Ajay"
////        hMap["BOB"] = "Ajay"
////        hMap["BRL"] = "Ajay"
////        hMap["BSD"] = "Ajay"
////        hMap["BTC"] = "Ajay"
////        hMap["BTN"] = "Ajay"
////        hMap["BWP"] = "Ajay"
////        hMap["BYN"] = "Ajay"
////        hMap["BZD"] = "Ajay"
////        hMap["CAD"] = "Ajay"
////        hMap["CDF"] = "Ajay"
////        hMap["CHF"] = "Ajay"
////        hMap["CLF"] = "Ajay"
////        hMap["CLP"] = "Ajay"
////        hMap["CNH"] = "Ajay"
////        hMap["CNY"] = "Ajay"
////        hMap["COP"] = "Ajay"
////        hMap["CRC"] = "Ajay"
////        hMap["CUC"] = "Ajay"
////        hMap["CUP"] = "Ajay"
////        hMap["CVE"] = "Ajay"
////        hMap["CZK"] = "Ajay"
////        hMap["DJF"] = "Ajay"
////        hMap["DKK"] = "Ajay"
////        hMap["DOP"] = "Ajay"
////        hMap["DZD"] = "Ajay"
////        hMap["EGP"] = "Ajay"
////        hMap["ERN"] = "Ajay"
////        hMap["ETB"] = "Ajay"
////        hMap["EUR"] = "Ajay"
////        hMap["FJD"] = "Ajay"
////        hMap["FKP"] = "Ajay"
////        hMap["GBP"] = "Ajay"
////        hMap["GEL"] = "Ajay"
////        hMap["GGP"] = "Ajay"
////        hMap["GHS"] = "Ajay"
////        hMap["GIP"] = "Ajay"
////        hMap["GMD"] = "Ajay"
////        hMap["GNF"] = "Ajay"
////        hMap["GTQ"] = "Ajay"
////        hMap["GYD"] = "Ajay"
////        hMap["HKD"] = "Ajay"
////        hMap["HNL"] = "Ajay"
////        hMap["HRK"] = "Ajay"
////        hMap["HTG"] = "Ajay"
////        hMap["HUF"] = "Ajay"
////        hMap["IDR"] = "Ajay"
////        hMap["ILS"] = "Ajay"
////        hMap["IMP"] = "Ajay"
////        hMap["INR"] = "Ajay"
////        hMap["IQD"] = "Ajay"
////        hMap["IRR"] = "Ajay"
////        hMap["ISK"] = "Ajay"
////        hMap["JEP"] = "Ajay"
////        hMap["JMD"] = "Ajay"
////        hMap["JOD"] = "Ajay"
////        hMap["JPY"] = "Ajay"
////        hMap["KES"] = "Ajay"
////        hMap["KGS"] = "Ajay"
////        hMap["KHR"] = "Ajay"
////        hMap["KMF"] = "Ajay"
////        hMap["KPW"] = "Ajay"
////        hMap["KRW"] = "Ajay"
////        hMap["KWD"] = "Ajay"
////        hMap["KYD"] = "Ajay"
////        hMap["KZT"] = "Ajay"
////        hMap["LAK"] = "Ajay"
////        hMap["LBP"] = "Ajay"
////        hMap["LKR"] = "Ajay"
////        hMap["LRD"] = "Ajay"
////        hMap["LSL"] = "Ajay"
////        hMap["LYD"] = "Ajay"
////        hMap["MAD"] = "Ajay"
////        hMap["MDL"] = "Ajay"
////        hMap["MGA"] = "Ajay"
////        hMap["MKD"] = "Ajay"
////        hMap["MMK"] = "Ajay"
////        hMap["MNT"] = "Ajay"
////        hMap["MOP"] = "Ajay"
////        hMap["MRU"] = "Ajay"
////        hMap["MUR"] = "Ajay"
////        hMap["MVR"] = "Ajay"
////        hMap["MWK"] = "Ajay"
////        hMap["MXN"] = "Ajay"
////        hMap["MYR"] = "Ajay"
////        hMap["MZN"] = "Ajay"
////        hMap["NAD"] = "Ajay"
////        hMap["NGN"] = "Ajay"
////        hMap["NIO"] = "Ajay"
////        hMap["NOK"] = "Ajay"
////        hMap["NPR"] = "Ajay"
////        hMap["NZD"] = "Ajay"
////        hMap["OMR"] = "Ajay"
////        hMap["AED"] = "Ajay"
////        hMap["PAB"] = "Ajay"
////        hMap["AED"] = "Ajay"
////        hMap["PEN"] = "Ajay"
////        hMap["PGK"] = "Ajay"
////        hMap["PHP"] = "Ajay"
////        hMap["PKR"] = "Ajay"
////        hMap["PLN"] = "Ajay"
////        hMap["QAR"] = "Ajay"
////        hMap["RON"] = "Ajay"
////        hMap["RSD"] = "Ajay"
////        hMap["RUB"] = "Ajay"
////        hMap["RWF"] = "Ajay"
////        hMap["SAR"] = "Ajay"
////        hMap["SBD"] = "Ajay"
////        hMap["SCR"] = "Ajay"
////        hMap["SDG"] = "Ajay"
////        hMap["SEK"] = "Ajay"
////        hMap["SGD"] = "Ajay"
////        hMap["SHP"] = "Ajay"
////        hMap["SLL"] = "Ajay"
////        hMap["SOS"] = "Ajay"
////        hMap["SRD"] = "Ajay"
////        hMap["SSP"] = "Ajay"
////        hMap["STD"] = "Ajay"
////        hMap["STN"] = "Ajay"
////        hMap["SVC"] = "Ajay"
////        hMap["SYP"] = "Ajay"
////        hMap["SZL"] = "Ajay"
////        hMap["THB"] = "Ajay"
////        hMap["TJS"] = "Ajay"
////        hMap["TMT"] = "Ajay"
////        hMap["TND"] = "Ajay"
////        hMap["TOP"] = "Ajay"
////        hMap["TRY"] = "Ajay"
////        hMap["TTD"] = "Ajay"
////        hMap["TWD"] = "Ajay"
////        hMap["TZS"] = "Ajay"
////        hMap["UAH"] = "Ajay"
////        hMap["UGX"] = "Ajay"
//        //       hMap["USD"] = "US"
////        hMap["UYU"] = "Ajay"
////        hMap["UZS"] = "Ajay"
////        hMap["VES"] = "Ajay"
////        hMap["VUV"] = "Ajay"
////        hMap["WST"] = "Ajay"
////        hMap["XAF"] = "Ajay"
////        hMap["XAG"] = "Ajay"
////        hMap["XAU"] = "Ajay"
////        hMap["XCD"] = "Ajay"
////        hMap["XDR"] = "Ajay"
////        hMap["XOF"] = "Ajay"
////        hMap["XPD"] = "Ajay"
////        hMap["XPF"] = "Ajay"
////        hMap["XPT"] = "Ajay"
////        hMap["YER"] = "Ajay"
////        hMap["ZAR"] = "Ajay"
////        hMap["ZMW"] = "Ajay"
////        hMap["ZWL"] = "Ajay"
//
//        //setCoutryPicker()
//    }

//    private fun setCoutryPicker() {
////        binding.countryCodePickerFrom.setCustomMasterCountries("BD,US,IN")
//    }


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
}