package com.android.coinranking.features.coins

import com.android.coinranking.core.functional.Result

interface CoinRepository {
  suspend fun getLocalCoins(): Result<List<Coin>>
  suspend fun updateCoins(): Result<List<Coin>>
  suspend fun getCoinById(id: String): Result<Coin?>
  suspend fun deleteCoinById(id: String): Result<Unit>
  suspend fun addCoins(coins: List<Coin>): Result<Unit>
}