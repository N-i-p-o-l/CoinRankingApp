package com.android.coinranking.core.di

import com.android.coinranking.core.platform.base.BaseActivity
import com.android.coinranking.features.coins.presentation.CoinFragment

interface Injector {
  fun inject(baseActivity: BaseActivity)
  fun inject(coinFragment: CoinFragment)
}