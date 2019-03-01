package com.android.coinranking.coin.core.provider

import com.android.coinranking.core.provider.CoroutineLauncher
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TestCoroutineLauncher @Inject constructor(): CoroutineLauncher, CoroutineScope {
  private val parentJob by lazy { Job() }

  @ExperimentalCoroutinesApi
  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Unconfined + parentJob

  override fun cancelAllJobs() { parentJob.cancel() }

  override fun start(block: suspend CoroutineScope.() -> Unit): Job = launch { runBlocking { block() } }

  override fun startInBackground(block: suspend CoroutineScope.() -> Unit): Job = launch { runBlocking { block() } }

  override suspend fun <T> callInBackground(block: suspend CoroutineScope.() -> T): T = runBlocking { block() }

  override suspend fun <T> CoroutineScope.defer(
    block: suspend CoroutineScope.() -> T): Deferred<T> = async { runBlocking { block() } }
}