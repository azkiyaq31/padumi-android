package com.padumi.core.model

import java.time.Instant

/** A garment saved in the user's wardrobe. [photoPath] points to the compressed app-private photo. */
data class WardrobeItem(
    val id: Long,
    val name: String,
    val category: ClothingCategory,
    val availability: Availability,
    val photoPath: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
