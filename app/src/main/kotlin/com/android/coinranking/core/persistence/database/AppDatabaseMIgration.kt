package com.android.coinranking.core.persistence.database

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun getDatabaseMigrations() = arrayOf(MigrationFrom1To2)

object MigrationFrom1To2: Migration(DATABASE_VERSION_1, DATABASE_VERSION_2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    logMigration()
  }
}

// region Logcat
private fun Migration.tag(): String = javaClass.simpleName

private fun Migration.logMigration() {
  Log.d(tag(), "Running migration ${tag()}")
}

private fun Migration.logLegacyError() {
  Log.d(tag(), "Legacy table not found: no action required")
}

private fun Migration.logLegacySuccess() {
  Log.d(tag(), "Legacy table found: data migrated successfully")
}
// endregion