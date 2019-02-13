package com.android.coinranking.features.coins.data

import androidx.room.Entity
import com.android.coinranking.core.functional.Mapper
import com.android.coinranking.features.coins.Coin
import javax.inject.Inject

const val COIN_DTO_COLUMN_ID = "id"
const val COIN_DTO_TABLE_NAME = "Coins"

@Entity(tableName = COIN_DTO_TABLE_NAME, primaryKeys = [COIN_DTO_COLUMN_ID])
data class CoinDto(val id: Long, val name: String, val description: String,
                   val iconUrl: String, val price: String)

class CoinDtoMapper @Inject constructor(): Mapper<CoinDto, Coin> {
  override fun map(param: CoinDto) = with(param) {
    Coin(id, name, description, iconUrl, price)
  }

  override fun mapReverse(param: Coin) = with(param) {
    CoinDto(id, name, description, iconUrl, price)
  }
}