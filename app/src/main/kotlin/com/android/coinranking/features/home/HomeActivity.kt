package com.android.coinranking.features.home

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.android.coinranking.R
import com.android.coinranking.core.platform.base.BaseActivity
import com.android.coinranking.features.coins.presentation.CoinFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity: BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    setSupportActionBar(activity_home_toolBar)
    showCoins()
  }

  fun showCoins() {
    supportFragmentManager.beginTransaction()
      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
      .replace(R.id.activity_home_container, CoinFragment.newInstance())
      .addToBackStack(null)
      .commit()
  }

  fun showProgressUpdate(visible: Int) {
    activity_home_loading.visibility = visible
  }

  fun showUpdateError(visible: Int) {
    activity_home_error.visibility = visible
  }
}