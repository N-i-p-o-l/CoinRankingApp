package com.android.coinranking.core.di.modules

import androidx.lifecycle.ViewModel
import com.android.coinranking.core.di.modules.viewmodel.ViewModelKey
import com.android.coinranking.features.coins.CoinRepository
import com.android.coinranking.features.coins.data.CoinDataSource
import com.android.coinranking.features.coins.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CoinModule {

  @Binds
  abstract fun bindCoinRepository(coinDataSource: CoinDataSource): CoinRepository

  @Binds
  @IntoMap
  @ViewModelKey(CoinViewModel::class)
  abstract fun bindCoinViewModel(coinViewModel: CoinViewModel): ViewModel
}