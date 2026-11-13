package com.padumi.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface GreetingDao {
    @Query("SELECT message FROM greeting WHERE id = 1")
    fun observeMessage(): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(greeting: GreetingEntity)
}
