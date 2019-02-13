package com.android.coinranking.core.provider

import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface CoroutineLauncher {
  fun cancelAllJobs()
  fun start(block: suspend CoroutineScope.() -> Unit): Job
  fun startInBackground(block: suspend CoroutineScope.() -> Unit): Job
  suspend fun <T> callInBackground(block: suspend CoroutineScope.() -> T): T
  suspend fun <T> CoroutineScope.defer(block: suspend CoroutineScope.() -> T): Deferred<T>
}

class CoroutineLauncherDelegate @Inject constructor(): CoroutineLauncher, CoroutineScope {
  private val parentJob by lazy { Job() }
  private val main get() = Dispatchers.Main
  private val background get() = Dispatchers.IO

  override val coroutineContext: CoroutineContext get() = main + parentJob

  override fun cancelAllJobs() { parentJob.cancel() }
  override fun start(block: suspend  CoroutineScope.() -> Unit): Job = launch { block() }
  override fun startInBackground(block: suspend CoroutineScope.() -> Unit): Job
      = launch(background) { block() }
  override suspend fun <T> callInBackground(block: suspend CoroutineScope.() -> T): T
      = withContext(background) { block() }
  override suspend fun <T> CoroutineScope.defer(block: suspend CoroutineScope.() -> T): Deferred<T>
      = coroutineScope { async(background) { block() } }
}