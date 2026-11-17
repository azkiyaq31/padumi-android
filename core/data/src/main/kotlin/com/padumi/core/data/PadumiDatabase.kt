package com.padumi.core.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GreetingEntity::class, WardrobeItemEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
)
internal abstract class PadumiDatabase : RoomDatabase() {
    abstract fun greetingDao(): GreetingDao

    abstract fun wardrobeItemDao(): WardrobeItemDao

    companion object {
        const val DATABASE_NAME = "padumi.db"
    }
}
