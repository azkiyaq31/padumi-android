package com.padumi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.padumi.core.designsystem.PadumiSpacing
import com.padumi.feature.home.HomeRoute

@Composable
fun PadumiAppShell(todayContent: @Composable () -> Unit = { HomeRoute() }) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: PadumiDestination.TODAY.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                PadumiDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text(destination.label.take(1)) },
                        label = { Text(destination.label) },
                        modifier =
                            Modifier.semantics {
                                contentDescription = "${destination.label} destination"
                            },
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = PadumiDestination.TODAY.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(PadumiDestination.TODAY.route) {
                Box(modifier = Modifier.testTag(PadumiDestination.TODAY.screenTag)) {
                    todayContent()
                }
            }
            PadumiDestination.entries
                .filterNot { it == PadumiDestination.TODAY }
                .forEach { destination ->
                    composable(destination.route) {
                        PlaceholderDestination(destination)
                    }
                }
        }
    }
}

@Composable
private fun PlaceholderDestination(destination: PadumiDestination) {
    Box(
        modifier = Modifier.fillMaxSize().testTag(destination.screenTag),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = destination.label,
            modifier = Modifier.padding(PadumiSpacing.screenMarginMin),
        )
    }
}
