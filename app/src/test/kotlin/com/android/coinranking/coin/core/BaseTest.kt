package com.android.coinranking.coin.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.coinranking.coin.core.provider.TestCoroutineLauncher
import com.android.coinranking.core.provider.CoroutineLauncher
import org.junit.Rule

abstract class BaseTest {

  /* ViewModel tests should use jUnit 4 as rules are not supported in jUnit 5 */
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  protected val testCoroutineLauncher: CoroutineLauncher = TestCoroutineLauncher()
}