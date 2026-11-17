package com.padumi.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WardrobeItemDao {
    @Query("SELECT * FROM wardrobe_item ORDER BY created_at DESC, id DESC")
    fun observeItems(): Flow<List<WardrobeItemEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: WardrobeItemEntity): Long
}
