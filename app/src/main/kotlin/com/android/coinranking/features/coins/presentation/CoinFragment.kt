package com.android.coinranking.features.coins.presentation

import android.os.Bundle
import com.android.coinranking.R
import com.android.coinranking.core.extension.error
import com.android.coinranking.core.extension.observe
import com.android.coinranking.core.extension.withLinearLayoutManager
import com.android.coinranking.core.platform.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_coin.*

class CoinFragment: BaseFragment() {

  companion object Instance {
    fun newInstance(args: Bundle? = null): CoinFragment {
      val fragment = CoinFragment()
      fragment.arguments = args
      return fragment
    }
  }

  private val coinViewModel by lazy { viewModelFactory.get<CoinViewModel>(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    injector.inject(this)
    coinViewModel.run {
      error(error, ::handleError)
      observe(state, ::handleState)
    }
  }

  override var layoutID = R.layout.fragment_coin
  override fun init() {
    setupRecyclerView()
    coinViewModel.getAllCoins()
  }

  private fun handleState(state: CoinViewModel.State) {
    when(state) {
      is CoinViewModel.State.ShowList -> showCoins(state)
    }
  }

  private fun showCoins(state: CoinViewModel.State.ShowList) {}

  override fun handleError(error: Throwable) {
    super.handleError(error)
    //ToDo show error view
  }

  private fun setupRecyclerView() {
    fragment_coin_recycle.withLinearLayoutManager()
  }
}