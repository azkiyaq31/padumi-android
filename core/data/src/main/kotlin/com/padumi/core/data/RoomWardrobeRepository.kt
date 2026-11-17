package com.padumi.core.data

import com.padumi.core.domain.WardrobeRepository
import com.padumi.core.model.Availability
import com.padumi.core.model.NewWardrobeItem
import com.padumi.core.model.WardrobeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Clock
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RoomWardrobeRepository
    @Inject
    constructor(
        private val dao: WardrobeItemDao,
        private val clock: Clock,
    ) : WardrobeRepository {
        override fun observeItems(): Flow<List<WardrobeItem>> =
            dao.observeItems().map { rows ->
                rows.map { it.toModel() }
            }

        override suspend fun addItem(item: NewWardrobeItem): Long {
            val now = clock.millis()
            return dao.insert(
                WardrobeItemEntity(
                    name = item.name,
                    category = WardrobeCodes.code(item.category),
                    availability = WardrobeCodes.code(Availability.AVAILABLE),
                    photoPath = item.photoPath,
                    createdAtEpochMillis = now,
                    updatedAtEpochMillis = now,
                ),
            )
        }
    }

private fun WardrobeItemEntity.toModel() =
    WardrobeItem(
        id = id,
        name = name,
        category = WardrobeCodes.category(category),
        availability = WardrobeCodes.availability(availability),
        photoPath = photoPath,
        createdAt = Instant.ofEpochMilli(createdAtEpochMillis),
        updatedAt = Instant.ofEpochMilli(updatedAtEpochMillis),
    )
