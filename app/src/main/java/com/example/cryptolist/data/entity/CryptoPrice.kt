package com.example.cryptolist.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoPrice")
data class CryptoPrice(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ForeignKey(entity = CryptoCurrency::class, parentColumns = ["id"], childColumns = ["cryptoName"])
    val cryptoName: String,
    val priceUsd: String,
    val time: Long
)