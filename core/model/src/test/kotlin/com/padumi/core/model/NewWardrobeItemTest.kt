package com.padumi.core.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NewWardrobeItemTest {
    @Test
    fun `name is trimmed`() {
        assertThat(NewWardrobeItem("  Blue shirt  ", ClothingCategory.TOP, "photos/a.jpg").name)
            .isEqualTo("Blue shirt")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `blank name is rejected`() {
        NewWardrobeItem("   ", ClothingCategory.TOP, "photos/a.jpg")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `blank photo path is rejected`() {
        NewWardrobeItem("Blue shirt", ClothingCategory.TOP, " ")
    }

    @Test
    fun `categories match the approved fixed list`() {
        assertThat(ClothingCategory.entries.map { it.label })
            .containsExactly("Top", "Bottom", "One-piece", "Outerwear", "Footwear", "Accessory", "Other")
            .inOrder()
    }

    @Test
    fun `statuses match the approved list`() {
        assertThat(Availability.entries.map { it.label })
            .containsExactly("Available", "In Laundry", "Unavailable")
            .inOrder()
    }
}
