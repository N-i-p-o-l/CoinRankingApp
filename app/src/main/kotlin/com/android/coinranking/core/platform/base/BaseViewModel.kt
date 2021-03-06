package com.android.coinranking.core.platform.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.coinranking.core.provider.CoroutineLauncher

abstract class BaseViewModel(coroutineLauncher: CoroutineLauncher): ViewModel(),
    CoroutineLauncher by coroutineLauncher {

  val error by lazy { MutableLiveData<Throwable>() }

  @CallSuper
  override fun onCleared() {
    super.onCleared()
    cancelAllJobs()
  }

  protected fun handleError(error: Throwable) {
    this.error.postValue(error)
  }
}