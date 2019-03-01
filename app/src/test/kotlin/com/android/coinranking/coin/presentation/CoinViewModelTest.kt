package com.android.coinranking.coin.presentation

import com.android.coinranking.coin.core.BaseTest
import com.android.coinranking.coin.core.MockDataProvider
import com.android.coinranking.coin.core.extension.*
import com.android.coinranking.core.functional.Failure
import com.android.coinranking.core.functional.Success
import com.android.coinranking.features.coins.Coin
import com.android.coinranking.features.coins.CoinRepository
import com.android.coinranking.features.coins.presentation.CoinModel
import com.android.coinranking.features.coins.presentation.CoinModelMapper
import com.android.coinranking.features.coins.presentation.CoinViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.spy
import java.net.ConnectException

class CoinViewModelTest: BaseTest() {
  private val mockCoinRepository = mock<CoinRepository>()
  private val mockCoinModelMapper = mock<CoinModelMapper>()

  private val mockCoinsList = mock<List<Coin>>()
  private val mockCoinModelList = mock<List<CoinModel>>()

  private lateinit var viewModel: CoinViewModel

  @Before
  fun setup() {
    viewModel = spy(CoinViewModel(
      mockCoinRepository,
      mockCoinModelMapper,
      testCoroutineLauncher
    ))
  }

  @Test
  fun whenGetAllCoinsIsCalledAndLocalDataIsEmptyAndAirPlaneModeOnThenReceivesError() {
    givenSuspend { mockCoinRepository.getLocalCoins() }.willReturn(Success(mockCoinsList))
    givenSuspend { mockCoinRepository.updateCoins() }.willReturn(Failure(ConnectException()))

    //WHEN
    viewModel.getAllCoins()

    //THEN
    viewModel.loading currentEventShouldBe Unit
    viewModel.coinsLocalData.shouldNeverReceiveValues()
    viewModel.updateLoading currentEventShouldBe Unit
    viewModel.coinsNetworkData.shouldNeverReceiveValues()
    viewModel.updateError.shouldNeverReceiveValues()
    viewModel.error.shouldReceiveValues()
  }

  @Test
  fun whenGetAllCoinsIsCalledAndLocalDataIsEmptyAndAirPlaneModeOffThenReceivesRemoteValue() {
    givenSuspend { mockCoinRepository.getLocalCoins() }.willReturn(Success(mockCoinsList))
    givenSuspend { mockCoinRepository.updateCoins() }.willReturn(Success(mockCoinsList))

    //WHEN
    viewModel.getAllCoins()

    //THEN
    viewModel.loading currentEventShouldBe Unit
    viewModel.coinsLocalData.shouldNeverReceiveValues()
    viewModel.updateLoading currentEventShouldBe Unit
    viewModel.coinsNetworkData.shouldReceiveValues()
    viewModel.updateError.shouldNeverReceiveValues()
    viewModel.error.shouldNeverReceiveValues()
  }

  @Test
  fun whenGetAllCoinsIsCalledAndLocalDataIsNotEmptyAndAirPlaneModeOnThenReceivesLocalValueAndUpdateError() {
    givenSuspend { mockCoinRepository.getLocalCoins() }.willReturn(Success(MockDataProvider.coinsList))
    given(mockCoinModelMapper.mapList(safeAny())).willReturn(mockCoinModelList)
    givenSuspend { mockCoinRepository.updateCoins() }.willReturn(Failure(ConnectException()))

    //WHEN
    viewModel.getAllCoins()

    //THEN
    viewModel.loading currentEventShouldBe Unit
    viewModel.coinsLocalData currentValueShouldBe mockCoinModelList
    viewModel.updateLoading currentEventShouldBe Unit
    viewModel.coinsNetworkData.shouldNeverReceiveValues()
    viewModel.updateError.shouldReceiveValues()
    viewModel.error.shouldNeverReceiveValues()
  }

  @Test
  fun whenGetAllCoinsIsCalledAndLocalDataIsNotEmptyAndAirPlaneModeOffThenReceivesLocalValueAndRemoteValue() {
    givenSuspend { mockCoinRepository.getLocalCoins() }.willReturn(Success(MockDataProvider.coinsList))
    given(mockCoinModelMapper.mapList(safeAny())).willReturn(mockCoinModelList)
    givenSuspend { mockCoinRepository.updateCoins() }.willReturn(Success(mockCoinsList))

    //WHEN
    viewModel.getAllCoins()

    //THEN
    viewModel.loading currentEventShouldBe Unit
    viewModel.coinsLocalData currentValueShouldBe mockCoinModelList
    viewModel.updateLoading currentEventShouldBe Unit
    viewModel.coinsNetworkData currentValueShouldBe mockCoinModelList
    viewModel.updateError.shouldNeverReceiveValues()
    viewModel.error.shouldNeverReceiveValues()
  }
}