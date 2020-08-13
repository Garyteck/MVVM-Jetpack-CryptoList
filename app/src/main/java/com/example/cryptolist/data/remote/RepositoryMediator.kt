package com.example.cryptolist.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cryptolist.api.NetworkInterface
import com.example.cryptolist.data.entity.CryptoCurrency
import com.example.cryptolist.data.local.CryptoDatabase
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RepositoryMediator(
    val db: CryptoDatabase,
    val api: NetworkInterface
) : RemoteMediator<Int, CryptoCurrency>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CryptoCurrency>
    ): MediatorResult {

        try {

        val offset : Int = when(loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {

                db.cryptoDao().getCryptoInDb()
            }
        }

        val cryptos = api.getCryptos(limit = state.config.pageSize, offset = offset).body()?.data

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                db.cryptoDao().deleteAllCryptos()
            }

            cryptos?.let {
                db.cryptoDao().insertCryptos(it)
            }
        }


        return MediatorResult.Success( endOfPaginationReached = cryptos?.isEmpty() ?: true)

        } catch (e : IOException) {
            return MediatorResult.Error(e)
        } catch (e : HttpException) {
           return  MediatorResult.Error(e)
        }

    }
}