package com.padumi.core.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PadumiDesignTokensTest {
    @Test
    fun `approved soft minimalism colors are exposed as tokens`() {
        assertThat(PadumiColors.primary).isEqualTo(Color(0xFF6750A4))
        assertThat(PadumiColors.onPrimary).isEqualTo(Color.White)
        assertThat(PadumiColors.primaryContainer).isEqualTo(Color(0xFFEADDFF))
        assertThat(PadumiColors.background).isEqualTo(Color(0xFFF7F5FA))
        assertThat(PadumiColors.surface).isEqualTo(Color.White)
        assertThat(PadumiColors.onSurface).isEqualTo(Color(0xFF1D1B20))
    }

    @Test
    fun `material theme uses approved light palette and typography`() {
        assertThat(PadumiColors.lightScheme.primary).isEqualTo(PadumiColors.primary)
        assertThat(PadumiColors.lightScheme.primaryContainer).isEqualTo(PadumiColors.primaryContainer)
        assertThat(PadumiColors.lightScheme.background).isEqualTo(PadumiColors.background)
        assertThat(PadumiColors.lightScheme.surface).isEqualTo(PadumiColors.surface)
        assertThat(PadumiTypography.material.bodyLarge.fontFamily).isEqualTo(PadumiTypography.bodyLarge.fontFamily)
        assertThat(PadumiTypography.material.titleLarge.fontFamily).isEqualTo(PadumiTypography.titleLarge.fontFamily)
    }

    @Test
    fun `spacing and corner radius tokens match written design specification`() {
        assertThat(PadumiSpacing.grid).isEqualTo(8.dp)
        assertThat(PadumiSpacing.screenMarginMin).isEqualTo(16.dp)
        assertThat(PadumiSpacing.screenMarginMax).isEqualTo(24.dp)
        assertThat(PadumiRadii.itemCard).isEqualTo(12.dp)
        assertThat(PadumiRadii.outfitCollage).isEqualTo(16.dp)
        assertThat(PadumiRadii.featuredCard).isEqualTo(20.dp)
    }

    @Test
    fun `typography follows material baseline sizes`() {
        assertThat(PadumiTypography.bodyLarge.fontSize).isEqualTo(16.sp)
        assertThat(PadumiTypography.titleLarge.fontSize).isEqualTo(22.sp)
        assertThat(PadumiTypography.displaySmall.fontSize).isEqualTo(36.sp)
    }
}
