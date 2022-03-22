package com.example.currencyconverter

import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class AppBaseActivity : AppCompatActivity() {

    private val TAG: String = "AppBaseActivity"

    fun setToolbarWithoutBackButton(mToolbar: Toolbar) {
        setSupportActionBar(mToolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        Log.d(TAG, "onStart Called")
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

}