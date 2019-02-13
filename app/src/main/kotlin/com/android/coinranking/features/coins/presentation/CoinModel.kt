package com.android.coinranking.features.coins.presentation

import com.android.coinranking.core.functional.Mapper
import com.android.coinranking.core.platform.base.BaseViewType
import com.android.coinranking.features.coins.Coin
import javax.inject.Inject

data class CoinModel(val id: Long,
                     val name: String, val description: String,
                     val iconUrl: String, val price: String): BaseViewType {
  companion object {
    @JvmStatic val VIEW_TYPE = CoinModel::class.hashCode()
  }

  override fun getViewType() = VIEW_TYPE
}

class CoinModelMapper @Inject constructor(): Mapper<Coin, CoinModel> {
  override fun map(param: Coin): CoinModel = with(param) {
    CoinModel(id, name, description, iconUrl, price)
  }

  override fun mapReverse(param: CoinModel): Coin = with(param) {
    Coin(id, name, description, iconUrl, price)
  }
}