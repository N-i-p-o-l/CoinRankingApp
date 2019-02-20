package com.android.coinranking.features.coins.presentation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.coinranking.R
import com.android.coinranking.core.extension.error
import com.android.coinranking.core.extension.observe
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
    fragment_coin_recycle.setHasFixedSize(true)
    fragment_coin_recycle.layoutManager = LinearLayoutManager(
      activity, RecyclerView.VERTICAL, false)

    coinViewModel.getAllCoins()
  }

  private fun handleState(state: CoinViewModel.State) {
    when(state) {
      is CoinViewModel.State.ShowList -> showCoins(state)
    }
  }

  private fun showCoins(state: CoinViewModel.State.ShowList) {
    fragment_coin_recycle.adapter = CoinAdapter(state.coins)
  }

  override fun handleError(error: Throwable) {
    super.handleError(error)
    //ToDo show error view
  }
}