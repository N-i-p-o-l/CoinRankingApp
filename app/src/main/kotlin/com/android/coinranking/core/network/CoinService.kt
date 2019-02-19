package com.android.coinranking.core.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CoinService {
  @GET("./Coins") fun getCoins(): Deferred<ApiResponse<Coins>>
}