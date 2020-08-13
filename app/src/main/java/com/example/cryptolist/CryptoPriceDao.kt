package com.example.cryptolist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoPriceDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    fun insertCryptoPrice(cryptoPrice : List<CryptoPrice>)

    @Query("SELECT * FROM CryptoPrice WHERE cryptoName LIKE :cryptoName")
    fun getCryptoPrice(cryptoName :String ) : LiveData<List<CryptoPrice>>
}
