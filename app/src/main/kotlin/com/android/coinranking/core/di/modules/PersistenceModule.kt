package com.android.coinranking.core.di.modules

import android.content.Context
import androidx.room.Room
import com.android.coinranking.core.persistence.database.AppDatabase
import com.android.coinranking.core.persistence.database.AppDatabaseCallback
import com.android.coinranking.core.persistence.database.DATABASE_NAME
import com.android.coinranking.core.persistence.database.getDatabaseMigrations
import com.android.coinranking.features.coins.data.CoinDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PersistenceModule {
  @Provides
  @Singleton
  @JvmStatic
  fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
      .addMigrations(*getDatabaseMigrations())
      .addCallback(AppDatabaseCallback)
      .build()

  @Provides
  @JvmStatic
  fun provideCoinDao(appDatabase: AppDatabase): CoinDao = appDatabase.getCoinDao()
}