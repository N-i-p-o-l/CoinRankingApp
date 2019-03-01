package com.android.coinranking.features.coins.presentation

import androidx.lifecycle.MutableLiveData
import com.android.coinranking.core.functional.*
import com.android.coinranking.core.platform.MutableLiveEvent
import com.android.coinranking.core.platform.base.BaseViewModel
import com.android.coinranking.core.platform.postEvent
import com.android.coinranking.core.provider.CoroutineLauncher
import com.android.coinranking.features.coins.CoinRepository
import javax.inject.Inject

class CoinViewModel @Inject constructor(
  private val coinRepository: CoinRepository,
  private val coinModelMapper: CoinModelMapper,
  coroutineLauncher: CoroutineLauncher): BaseViewModel(coroutineLauncher) {

  val loading by lazy { MutableLiveEvent<Unit>() }
  val updateError by lazy { MutableLiveEvent<Unit>() }
  val updateLoading by lazy { MutableLiveEvent<Unit>() }
  val coinsLocalData by lazy { MutableLiveData<List<CoinModel>>() }
  val coinsNetworkData by lazy { MutableLiveData<List<CoinModel>>() }

  fun getAllCoins() {
      startInBackground {
        loading.postEvent(Unit)
        val localCoins = coinRepository.getLocalCoins().mapCatching { it.let(coinModelMapper::mapList) }
        checkLocalState(localCoins)

        updateLoading.postEvent(Unit)
        val remoteCoins = coinRepository.updateCoins().mapCatching { it.let(coinModelMapper::mapList) }
        checkNetworkState(remoteCoins, localCoins.rightOrNull()?.isEmpty() ?: true)
      }
  }

  private fun checkLocalState(coins: Result<List<CoinModel>>) {
    when {
      //First run app have empty data
      coins is Success -> if(coins.value.isNotEmpty()) coinsLocalData.postValue(coins.value)
      coins is Failure -> handleError(coins.error)
    }
  }

  private fun checkNetworkState(coins: Result<List<CoinModel>>, localIsEmpty: Boolean) {
    when {
      coins is Success -> coinsNetworkData.postValue(coins.value)
      //App gets no data neither local or remote
      coins is Failure -> if (localIsEmpty) handleError(coins.error)
        else updateError.postEvent(Unit) //App have local data
    }
  }
}