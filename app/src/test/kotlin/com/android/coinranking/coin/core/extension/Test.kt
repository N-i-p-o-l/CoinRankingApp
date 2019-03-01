@file:Suppress("UNCHECKED_CAST")

package com.android.coinranking.coin.core.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.coinranking.core.platform.Event
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.spy

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

fun <T> safeAny(): T = Mockito.any<T>() ?: null as T

fun <T> givenSuspend(methodCall: suspend () -> T): BDDMockito.BDDMyOngoingStubbing<T> =
  given(runBlocking { methodCall() })

infix fun <T> LiveData<T>.currentValueShouldBe(expectedValue: T) {
  var value: T? = null
  val observer = Observer<T> { value = it }

  observeForever(observer)
  assertEquals(expectedValue, value)
  removeObserver(observer)
}

infix fun <T> LiveData<Event<T>>.currentEventShouldBe(expectedValue: T) {
  currentValueShouldBe(Event(expectedValue))
}

fun <T> LiveData<T>.shouldNeverReceiveValues() {
  val observer = spy(Observer<T> {})
  observeForever(observer)
  verify(observer, never()).onChanged(any())
  removeObserver(observer)
}

fun <T> LiveData<T>.shouldReceiveValues() {
  val observer = spy(Observer<T> {})
  observeForever(observer)
  verify(observer, atLeastOnce()).onChanged(any())
  removeObserver(observer)
}