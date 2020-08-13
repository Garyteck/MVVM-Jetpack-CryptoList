package com.example.cryptolist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

const val PAGE_SIZE = 10

@ExperimentalPagingApi
class Repository(val db: CryptoDatabase , val api : NetworkInterface) {


    fun getCryptos() : Flow<PagingData<CryptoCurrency>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            initialKey = 0,
            remoteMediator = RepositoryMediator(db,api), // TODO dependency injection when creating remote mediator
            pagingSourceFactory = { db.cryptoDao().getCryptos() }
        ).flow
    }
}