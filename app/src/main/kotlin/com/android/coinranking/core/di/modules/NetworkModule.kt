package com.android.coinranking.core.di.modules

import com.android.coinranking.App
import com.android.coinranking.BuildConfig
import com.android.coinranking.core.network.CoinService
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

  const val TIMEOUT = 90L
  const val WRITE_TIMEOUT = 90L
  const val CONNECT_TIMEOUT = 15L
  const val CACHE_SIZE = 8 * 1024 * 1024L

  @Provides
  @Singleton
  @JvmStatic
  fun provideOkHttpCache(app: App) = Cache(app.cacheDir, CACHE_SIZE)

  @Provides
  @Singleton
  @JvmStatic
  fun provideOkHttp(cache: Cache): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    else logging.level = HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
      .connectTimeout(NetworkModule.CONNECT_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(NetworkModule.WRITE_TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(NetworkModule.TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(logging)
      .cache(cache)
      .build()
  }

  @Provides
  @Singleton
  @JvmStatic
  fun provideCoinService(client: OkHttpClient, gson: Gson): CoinService {
    val retrofit = Retrofit.Builder()
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create(gson))
      .baseUrl(BuildConfig.API_ENDPOINT)
      .client(client)
      .build()

    return retrofit.create(CoinService::class.java)
  }
}