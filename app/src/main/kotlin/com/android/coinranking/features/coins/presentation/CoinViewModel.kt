package com.android.coinranking.features.coins.presentation

import androidx.lifecycle.MutableLiveData
import com.android.coinranking.core.functional.*
import com.android.coinranking.core.platform.base.BaseViewModel
import com.android.coinranking.core.provider.CoroutineLauncher
import com.android.coinranking.features.coins.CoinRepository
import javax.inject.Inject

class CoinViewModel @Inject constructor(
  private val coinRepository: CoinRepository,
  private val coinModelMapper: CoinModelMapper,
  coroutineLauncher: CoroutineLauncher): BaseViewModel(coroutineLauncher) {

  val state by lazy { MutableLiveData<State>() }

  fun getAllCoins() {
    start {
      val coins = callInBackground {
        coinRepository.getAllCoins().mapCatching { it.let(coinModelMapper::mapList) }
      }
      when {
        coins is Success -> showCoins(coins.value)
        coins is Failure -> handleError(coins.error)
      }
    }
  }

  private fun showCoins(list: List<CoinModel>) {
    state.postValue(State.ShowList(list))
  }

  sealed class State {
    data class ShowList(val coins: List<CoinModel>): State()
  }
}