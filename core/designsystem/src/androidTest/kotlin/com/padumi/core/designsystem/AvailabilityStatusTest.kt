package com.padumi.core.designsystem

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class AvailabilityStatusTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun available_status_displays_readable_text_and_icon() {
        composeRule.setContent {
            PadumiTheme {
                AvailabilityStatus(status = GarmentAvailability.AVAILABLE)
            }
        }

        composeRule.onNodeWithText("Available").assertIsDisplayed()
        composeRule.onNodeWithText("✓").assertIsDisplayed()
    }
}
