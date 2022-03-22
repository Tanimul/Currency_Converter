package com.example.currencyconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.model.CurrencyListItem

@Database(entities = [CurrencyListItem::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var instance: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            if (instance == null) {

                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context, CurrencyDatabase::class.java,
                        "currency_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!

        }
    }
}