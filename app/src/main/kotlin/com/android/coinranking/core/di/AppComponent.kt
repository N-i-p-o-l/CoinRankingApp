package com.android.coinranking.core.di

import com.android.coinranking.App
import com.android.coinranking.core.di.modules.CoinModule
import com.android.coinranking.core.di.modules.CoreModule
import com.android.coinranking.core.di.modules.PersistenceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
  CoreModule::class,
  PersistenceModule::class,
  CoinModule::class])
@Singleton
interface AppComponent: Injector {

  @Component.Builder
  interface Builder {
    fun build(): AppComponent
    @BindsInstance fun application(application: App): Builder
  }

  fun inject(application: App)
}