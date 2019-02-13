package com.android.coinranking.core.platform.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.android.coinranking.App
import com.android.coinranking.core.di.modules.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

  val injector get() = (application as App).appComponent

  @Inject lateinit var viewModelFactory: ViewModelFactory

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    injector.inject(this)
    super.onCreate(savedInstanceState)
  }

  open fun handleError(error: Throwable) {
    error.printStackTrace()
  }
}