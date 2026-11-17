package com.padumi.core.model

/** Whether a garment can currently be worn. */
enum class Availability(
    val label: String,
) {
    AVAILABLE("Available"),
    IN_LAUNDRY("In Laundry"),
    UNAVAILABLE("Unavailable"),
}
