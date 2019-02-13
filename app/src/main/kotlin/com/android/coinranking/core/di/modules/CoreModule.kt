package com.android.coinranking.core.di.modules

import android.content.Context
import com.android.coinranking.App
import com.android.coinranking.core.provider.CoroutineLauncher
import com.android.coinranking.core.provider.CoroutineLauncherDelegate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [CoreModule.Binder::class])
object CoreModule {
  @Module interface Binder {
    @Binds fun bindContext(app: App): Context
    @Binds fun bindCoroutineLauncher(coroutineLauncherDelegate: CoroutineLauncherDelegate): CoroutineLauncher
  }

  @Provides @JvmStatic fun provideGson(): Gson = GsonBuilder().create()
}