package com.example.cryptolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


const val DB_NAME = "crypto-database"

@Database(
    entities = [CryptoCurrency::class, CryptoPrice::class],
    version = 4,
    exportSchema = true
)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun cryptoDao() : CryptoCurrencyDao
    abstract fun cryptoPriceDao() : CryptoPriceDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        )
                            .also { instance = it }
                }
        }

        private fun buildDatabase(context: Context): CryptoDatabase {
            return Room.databaseBuilder(
                context, CryptoDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration().build()
        }
    }
}