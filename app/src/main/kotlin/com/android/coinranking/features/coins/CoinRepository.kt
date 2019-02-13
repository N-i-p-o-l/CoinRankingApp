package com.android.coinranking.features.coins

import com.android.coinranking.core.functional.Result

interface CoinRepository {
  suspend fun getAllCoins(): Result<List<Coin>>
  suspend fun getCoinById(id: String): Result<Coin?>
  suspend fun deleteCoinById(id: String): Result<Unit>
  suspend fun addCoins(vararg coin: Coin): Result<Unit>
}