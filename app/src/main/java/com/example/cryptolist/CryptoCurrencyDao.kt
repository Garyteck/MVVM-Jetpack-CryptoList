package com.example.cryptolist

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CryptoCurrencyDao {

    @Query( "SELECT * FROM CryptoCurrency WHERE id LIKE :id LIMIT 1")
    fun getCryptoById(id : String) : LiveData<CryptoCurrency>

    @Query( "SELECT * FROM CryptoCurrency WHERE name LIKE :name LIMIT 1")
    fun getCryptoByName(name :String) : LiveData<CryptoCurrency>

    @Query( "SELECT COUNT(*) FROM CryptoCurrency ")
    suspend fun getCryptoInDb() : Int

    @Query("SELECT * FROM CryptoCurrency")
    fun getCryptos() : PagingSource<Int, CryptoCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptos(cryptos : List<CryptoCurrency>)

    @Query("DELETE FROM CryptoCurrency")
    suspend fun deleteAllCryptos()
}