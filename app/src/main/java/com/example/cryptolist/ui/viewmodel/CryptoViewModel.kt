package com.example.cryptolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.cryptolist.data.entity.CryptoCurrency
import com.example.cryptolist.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class CryptoViewModel @ExperimentalPagingApi constructor( repository: Repository) : ViewModel() {

    @ExperimentalPagingApi
    val cryptos : Flow<PagingData<CryptoCurrency>> = repository.getCryptos()
}