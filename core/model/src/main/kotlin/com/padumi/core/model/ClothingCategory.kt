package com.padumi.core.model

/** Fixed, gender-neutral clothing categories approved for the MVP. */
enum class ClothingCategory(
    val label: String,
) {
    TOP("Top"),
    BOTTOM("Bottom"),
    ONE_PIECE("One-piece"),
    OUTERWEAR("Outerwear"),
    FOOTWEAR("Footwear"),
    ACCESSORY("Accessory"),
    OTHER("Other"),
}
