package com.padumi.core.domain

import com.padumi.core.model.NewWardrobeItem
import com.padumi.core.model.WardrobeItem
import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    /** Active wardrobe items, newest first. */
    fun observeItems(): Flow<List<WardrobeItem>>

    /** Saves a new item as Available and returns its id. */
    suspend fun addItem(item: NewWardrobeItem): Long
}
