package com.android.coinranking

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.android.coinranking.core.di.AppComponent
import com.android.coinranking.core.di.DaggerAppComponent

class App : Application() {
  val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
    DaggerAppComponent
      .builder()
      .application(this)
      .build()
  }

  override fun onCreate() {
    super.onCreate()
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    appComponent.inject(this)
  }
}