package com.example.cryptolist

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterface {

    companion object {
        const val ENDPOINT = "https://api.coincap.io/v2/"
    }

    data class Cryptos (
        val data : List<CryptoCurrency>
    )

    data class Prices (
        val data : List<CryptoPrice>
    )

    @GET("assets")
    suspend fun getCryptos(
        @Query(value="limit") limit : Int = 100,
        @Query(value="offset") offset : Int = 100
    ) : Response<Cryptos>

    @GET("assets/{id}/history")
    suspend fun getPrices(
        @Path(value="id") id : String ,
        @Query(value="interval") interval : String
    ) : Response<Prices>
}