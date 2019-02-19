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
      startInBackground {
        val localCoins = coinRepository.getLocalCoins().mapCatching { it.let(coinModelMapper::mapList) }
        checkState(localCoins)

        val coins = coinRepository.updateCoins().mapCatching { it.let(coinModelMapper::mapList) }
        checkState(coins)
      }
  }

  private fun checkState(coins: Result<List<CoinModel>>) {
    when {
      coins is Success -> showCoins(coins.value)
      coins is Failure -> handleError(coins.error)
    }
  }

  private fun showCoins(list: List<CoinModel>) {
    state.postValue(State.ShowList(list))
  }

  sealed class State {
    data class ShowList(val coins: List<CoinModel>): State()
  }
}