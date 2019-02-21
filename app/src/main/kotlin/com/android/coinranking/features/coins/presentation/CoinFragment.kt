package com.android.coinranking.features.coins.presentation

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.coinranking.R
import com.android.coinranking.core.extension.error
import com.android.coinranking.core.extension.observe
import com.android.coinranking.core.extension.observeEvent
import com.android.coinranking.core.platform.base.BaseFragment
import com.android.coinranking.features.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_coin.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_loading.*

class CoinFragment: BaseFragment() {

  lateinit var homeActivity: HomeActivity

  companion object Instance {
    fun newInstance(args: Bundle? = null): CoinFragment {
      val fragment = CoinFragment()
      fragment.arguments = args
      return fragment
    }
  }

  private val coinViewModel by lazy { viewModelFactory.get<CoinViewModel>(this) }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    homeActivity = activity as HomeActivity
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    injector.inject(this)
    coinViewModel.run {
      error(error, ::handleError)
      observe(coinsLocalData, ::showCoins)
      observe(coinsNetworkData, ::showUpdateCoins)
      observeEvent(loading) {showLoading()}
      observeEvent(updateError) {showUpdateError()}
      observeEvent(updateLoading) {showUpdateLoading()}
    }
  }

  override var layoutID = R.layout.fragment_coin
  override fun init() {
    fragment_coin_recycle.setHasFixedSize(true)
    fragment_coin_recycle.layoutManager = LinearLayoutManager(
      activity, RecyclerView.VERTICAL, false)

    fragment_coin_refresh.setOnRefreshListener { coinViewModel.getAllCoins() }
    coinViewModel.getAllCoins()
  }

  private fun showLoading() {
    loading_layout.visibility = VISIBLE
    layout_error.visibility = GONE
  }

  private fun showCoins(coins: List<CoinModel>) {
    loading_layout.visibility = GONE
    fragment_coin_refresh.visibility = VISIBLE
    fragment_coin_refresh.isRefreshing = false
    fragment_coin_recycle.adapter = CoinAdapter(coins)
  }

  private fun showUpdateCoins(coins: List<CoinModel>) {
    loading_layout.visibility = GONE
    fragment_coin_recycle.adapter = CoinAdapter(coins)
    disableToolbarNotifications()
  }

  private fun showUpdateLoading() {
    homeActivity.apply {showProgressUpdate(VISIBLE); showUpdateError(GONE)}
  }

  private fun showUpdateError() {
    homeActivity.apply {showProgressUpdate(GONE); showUpdateError(VISIBLE)}
  }

  private fun disableToolbarNotifications() {
    homeActivity.apply {showProgressUpdate(GONE); showUpdateError(GONE)}
  }

  //Show error when first time load error
  override fun handleError(error: Throwable) {
    super.handleError(error)
    loading_layout.visibility = GONE
    layout_error.visibility = VISIBLE
    disableToolbarNotifications()
    layout_error_refresh.setOnClickListener {
      coinViewModel.getAllCoins()
    }
  }
}