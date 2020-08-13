package com.example.cryptolist

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class CryptoViewModel @ExperimentalPagingApi constructor(private val repository: Repository) : ViewModel() {

    @ExperimentalPagingApi
    val cryptos : Flow<PagingData<CryptoCurrency>> = repository.getCryptos()
}