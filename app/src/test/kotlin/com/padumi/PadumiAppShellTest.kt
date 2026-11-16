package com.padumi

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], application = Application::class)
class PadumiAppShellTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun launches_on_today_with_exactly_four_top_level_destinations() {
        composeRule.setContent { PadumiAppShell(todayContent = { Text("Today content") }) }

        composeRule
            .onAllNodesWithContentDescription(" destination", substring = true)
            .assertCountEquals(4)
        composeRule.onNodeWithContentDescription("Today destination").assertIsSelected()
        composeRule.onNodeWithTag(PadumiDestination.TODAY.screenTag).assertIsDisplayed()
        listOf("Wardrobe", "Outfits", "Planner").forEach { label ->
            composeRule
                .onNodeWithContentDescription("$label destination")
                .assertIsDisplayed()
                .assertIsNotSelected()
        }
    }

    @Test
    fun has_no_profile_or_login_destination() {
        composeRule.setContent { PadumiAppShell(todayContent = { Text("Today content") }) }

        composeRule.onNodeWithText("Profile").assertDoesNotExist()
        composeRule.onNodeWithText("Login", substring = true, ignoreCase = true).assertDoesNotExist()
        composeRule.onNodeWithText("Sign in", substring = true, ignoreCase = true).assertDoesNotExist()
    }

    @Test
    fun selecting_each_destination_shows_its_screen_and_selected_state() {
        composeRule.setContent { PadumiAppShell(todayContent = { Text("Today content") }) }

        listOf(
            PadumiDestination.WARDROBE,
            PadumiDestination.OUTFITS,
            PadumiDestination.PLANNER,
        ).forEach { destination ->
            composeRule
                .onNodeWithContentDescription("${destination.label} destination")
                .performClick()
            composeRule.onNodeWithTag(destination.screenTag).assertIsDisplayed()
            composeRule
                .onNodeWithContentDescription("${destination.label} destination")
                .assertIsSelected()
        }

        composeRule.onNodeWithContentDescription("Today destination").performClick()
        composeRule.onNodeWithContentDescription("Today destination").assertIsSelected()
        composeRule.onNodeWithTag(PadumiDestination.TODAY.screenTag).assertIsDisplayed()
        composeRule.onNodeWithTag(PadumiDestination.PLANNER.screenTag).assertDoesNotExist()
    }

    @Test
    fun back_from_a_secondary_destination_returns_to_today_with_today_selected() {
        composeRule.setContent { PadumiAppShell(todayContent = { Text("Today content") }) }

        composeRule.onNodeWithContentDescription("Wardrobe destination").performClick()
        composeRule.onNodeWithContentDescription("Planner destination").performClick()
        composeRule.runOnUiThread { composeRule.activity.onBackPressedDispatcher.onBackPressed() }

        composeRule.onNodeWithTag(PadumiDestination.TODAY.screenTag).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Today destination").assertIsSelected()
        composeRule.onNodeWithTag(PadumiDestination.PLANNER.screenTag).assertDoesNotExist()
        composeRule.onNodeWithTag(PadumiDestination.WARDROBE.screenTag).assertDoesNotExist()
    }

    @Test
    fun destinations_have_visible_labels_and_minimum_48dp_touch_targets() {
        composeRule.setContent { PadumiAppShell(todayContent = { Text("Today content") }) }

        PadumiDestination.entries.forEach { destination ->
            composeRule.onNodeWithText(destination.label, useUnmergedTree = true).assertIsDisplayed()
            composeRule
                .onNodeWithContentDescription("${destination.label} destination")
                .assertHeightIsAtLeast(48.dp)
                .assertWidthIsAtLeast(48.dp)
        }
    }
}
