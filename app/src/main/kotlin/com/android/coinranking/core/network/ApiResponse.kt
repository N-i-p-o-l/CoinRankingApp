package com.android.coinranking.core.network

import com.android.coinranking.features.coins.Coin

class ApiResponse<T>(val data: T)
class Coins(val coins: List<Coin>)