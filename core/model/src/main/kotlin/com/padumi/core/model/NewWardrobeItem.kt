package com.padumi.core.model

/** Details required to add a garment. New items always start as [Availability.AVAILABLE]. */
class NewWardrobeItem(
    name: String,
    val category: ClothingCategory,
    val photoPath: String,
) {
    val name: String = name.trim()

    init {
        require(this.name.isNotEmpty()) { "Name is required" }
        require(photoPath.isNotBlank()) { "Photo is required" }
    }
}
