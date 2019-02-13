package com.android.coinranking.core.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.coinranking.core.persistence.database.converter.MapStringBooleanTypeConverter
import com.android.coinranking.core.persistence.database.converter.MapStringIntTypeConverter
import com.android.coinranking.core.persistence.database.converter.StringListTypeConverter
import com.android.coinranking.features.coins.data.CoinDao
import com.android.coinranking.features.coins.data.CoinDto

const val DATABASE_NAME = "com.android.coinranking"

//region Database Versions
const val DATABASE_VERSION_1 = 1
const val DATABASE_VERSION_2 = 2
//endregion

@Database(
  entities = [CoinDto::class],
  version = DATABASE_VERSION_1,
  exportSchema = false
)
@TypeConverters(
  StringListTypeConverter::class,
  MapStringBooleanTypeConverter::class,
  MapStringIntTypeConverter::class
)
abstract class AppDatabase: RoomDatabase() {
  abstract fun getCoinDao(): CoinDao
}

object AppDatabaseCallback: RoomDatabase.Callback() {
  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)
    //Here you can put something to do when db initiated
  }
}