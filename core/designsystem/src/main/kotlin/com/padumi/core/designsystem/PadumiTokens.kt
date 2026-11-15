package com.padumi.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

object PadumiColors {
    val primary = Color(0xFF6750A4)
    val onPrimary = Color.White
    val primaryContainer = Color(0xFFEADDFF)
    val onPrimaryContainer = Color(0xFF21005D)
    val background = Color(0xFFF7F5FA)
    val surface = Color.White
    val onSurface = Color(0xFF1D1B20)
    val surfaceVariant = Color(0xFFE7E0EC)
    val onSurfaceVariant = Color(0xFF49454F)
    val outline = Color(0xFF79747E)

    internal val lightScheme =
        lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            background = background,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
        )
}

object PadumiSpacing {
    val grid = 8.dp
    val screenMarginMin = 16.dp
    val screenMarginMax = 24.dp
}

object PadumiRadii {
    val itemCard = 12.dp
    val outfitCollage = 16.dp
    val featuredCard = 20.dp
}

object PadumiTypography {
    private val roboto = FontFamily.Default
    private val base = Typography()

    val bodyLarge = base.bodyLarge.copy(fontFamily = roboto)
    val titleLarge = base.titleLarge.copy(fontFamily = roboto)
    val displaySmall = base.displaySmall.copy(fontFamily = roboto)

    internal val material =
        base.copy(
            displayLarge = base.displayLarge.copy(fontFamily = roboto),
            displayMedium = base.displayMedium.copy(fontFamily = roboto),
            displaySmall = displaySmall,
            headlineLarge = base.headlineLarge.copy(fontFamily = roboto),
            headlineMedium = base.headlineMedium.copy(fontFamily = roboto),
            headlineSmall = base.headlineSmall.copy(fontFamily = roboto),
            titleLarge = titleLarge,
            titleMedium = base.titleMedium.copy(fontFamily = roboto),
            titleSmall = base.titleSmall.copy(fontFamily = roboto),
            bodyLarge = bodyLarge,
            bodyMedium = base.bodyMedium.copy(fontFamily = roboto),
            bodySmall = base.bodySmall.copy(fontFamily = roboto),
            labelLarge = base.labelLarge.copy(fontFamily = roboto),
            labelMedium = base.labelMedium.copy(fontFamily = roboto),
            labelSmall = base.labelSmall.copy(fontFamily = roboto),
        )
}

internal object PadumiShapes {
    val material =
        Shapes(
            small = RoundedCornerShape(PadumiRadii.itemCard),
            medium = RoundedCornerShape(PadumiRadii.outfitCollage),
            large = RoundedCornerShape(PadumiRadii.featuredCard),
        )
}
