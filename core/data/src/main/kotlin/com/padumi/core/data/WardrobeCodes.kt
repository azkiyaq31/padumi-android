package com.padumi.core.data

import com.padumi.core.model.Availability
import com.padumi.core.model.ClothingCategory

/** Stable database codes. Never change an existing code; add new ones instead. */
internal object WardrobeCodes {
    private val categoryCodes =
        mapOf(
            ClothingCategory.TOP to "top",
            ClothingCategory.BOTTOM to "bottom",
            ClothingCategory.ONE_PIECE to "one_piece",
            ClothingCategory.OUTERWEAR to "outerwear",
            ClothingCategory.FOOTWEAR to "footwear",
            ClothingCategory.ACCESSORY to "accessory",
            ClothingCategory.OTHER to "other",
        )
    private val availabilityCodes =
        mapOf(
            Availability.AVAILABLE to "available",
            Availability.IN_LAUNDRY to "in_laundry",
            Availability.UNAVAILABLE to "unavailable",
        )

    fun code(category: ClothingCategory): String = categoryCodes.getValue(category)

    fun code(availability: Availability): String = availabilityCodes.getValue(availability)

    /** Unknown codes fall back to Other so one bad row can't break the whole wardrobe list. */
    fun category(code: String): ClothingCategory =
        categoryCodes.entries.firstOrNull { it.value == code }?.key ?: ClothingCategory.OTHER

    /** Unknown codes fall back to Unavailable so a corrupt row is never suggested as wearable. */
    fun availability(code: String): Availability =
        availabilityCodes.entries.firstOrNull { it.value == code }?.key ?: Availability.UNAVAILABLE
}
