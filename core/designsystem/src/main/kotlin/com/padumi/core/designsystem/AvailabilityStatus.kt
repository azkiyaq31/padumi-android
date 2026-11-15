package com.padumi.core.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Garment availability is conveyed with readable text plus a status symbol, not color alone. */
@Composable
fun AvailabilityStatus(
    status: GarmentAvailability,
    modifier: Modifier = Modifier,
) {
    val (label, symbol) =
        when (status) {
            GarmentAvailability.AVAILABLE -> "Available" to "✓"
            GarmentAvailability.IN_LAUNDRY -> "In Laundry" to "◷"
            GarmentAvailability.UNAVAILABLE -> "Unavailable" to "!"
        }
    Row(modifier = modifier) {
        Text(text = symbol, style = MaterialTheme.typography.labelLarge)
        Text(
            text = label,
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

enum class GarmentAvailability {
    AVAILABLE,
    IN_LAUNDRY,
    UNAVAILABLE,
}
