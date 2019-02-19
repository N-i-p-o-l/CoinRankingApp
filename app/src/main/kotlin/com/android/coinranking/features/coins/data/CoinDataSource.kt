package com.android.coinranking.features.coins.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.coinranking.core.functional.Result
import com.android.coinranking.core.functional.catching
import com.android.coinranking.core.network.CoinService
import com.android.coinranking.features.coins.Coin
import com.android.coinranking.features.coins.CoinRepository
import javax.inject.Inject

class CoinDataSource
@Inject constructor(private val coinDao: CoinDao,
                    private val coinService: CoinService,
                    private val coinDtoMapper: CoinDtoMapper): CoinRepository {
  override suspend fun getLocalCoins(): Result<List<Coin>> {
    return catching {
      val dbList = coinDao.getAllCoins().let(coinDtoMapper::mapList)
      dbList
    }
  }

  override suspend fun updateCoins(): Result<List<Coin>> {
    return catching {
      val ntList = coinService.getCoins().await()
      ntList.data.coins
    }
  }

  override suspend fun getCoinById(id: String): Result<Coin?> =
    catching { coinDao.getCoinById(id)?.let(coinDtoMapper::map) }

  override suspend fun deleteCoinById(id: String) =
    catching { coinDao.deleteCoinById(id) }

  override suspend fun addCoins(coins: List<Coin>) =
    catching { coins.map(coinDtoMapper::mapReverse).let(coinDao::addCoins) }
}

@Dao interface CoinDao {
  @Query("select * from $COIN_DTO_TABLE_NAME")
  fun getAllCoins(): List<CoinDto>

  @Query("select * from $COIN_DTO_TABLE_NAME where $COIN_DTO_COLUMN_ID = :id")
  fun getCoinById(id: String): CoinDto?

  @Query("delete from $COIN_DTO_TABLE_NAME where $COIN_DTO_COLUMN_ID = :id")
  fun deleteCoinById(id: String)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addCoins(list: List<CoinDto>)
}