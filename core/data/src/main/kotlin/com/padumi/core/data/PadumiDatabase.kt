package com.padumi.core.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GreetingEntity::class],
    version = 1,
    exportSchema = false,
)
internal abstract class PadumiDatabase : RoomDatabase() {
    abstract fun greetingDao(): GreetingDao

    companion object {
        const val DATABASE_NAME = "padumi.db"
    }
}
