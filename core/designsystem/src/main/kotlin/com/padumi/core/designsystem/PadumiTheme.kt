package com.padumi.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PadumiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PadumiColors.lightScheme,
        typography = PadumiTypography.material,
        shapes = PadumiShapes.material,
        content = content,
    )
}
