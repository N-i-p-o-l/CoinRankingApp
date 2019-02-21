package com.android.coinranking.core.platform.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

  protected val injector by lazy { (activity as BaseActivity).injector }
  protected val viewModelFactory by lazy { (activity as BaseActivity).viewModelFactory }

  abstract fun init()
  abstract var layoutID: Int

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View?
      = inflater.inflate(layoutID, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
  }

  open fun handleError(error: Throwable) {
    error.printStackTrace()
  }
}