package com.castle.sefirah.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.castle.sefirah.navigation.Graph
import com.castle.sefirah.navigation.MainRouteScreen
import com.castle.sefirah.presentation.devices.DevicesScreen
import com.castle.sefirah.presentation.home.HomeScreen
import com.castle.sefirah.presentation.settings.SettingsScreen
import com.castle.sefirah.navigation.transitions.NavigationTransitions

@Composable
fun MainNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    innerPadding: PaddingValues,
    searchQuery: String
) {
    NavHost(
        navController = homeNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainRouteScreen.HomeScreen.route,
        modifier = Modifier.padding(innerPadding),
        enterTransition = { NavigationTransitions.rootEnterTransition(this) },
        exitTransition = { NavigationTransitions.rootExitTransition(this) },
        popEnterTransition = { NavigationTransitions.rootPopEnterTransition(this) },
        popExitTransition = { NavigationTransitions.rootPopExitTransition(this) }
    ) {
        composable(
            route = MainRouteScreen.HomeScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    MainRouteScreen.DevicesScreen.route,
                    MainRouteScreen.SettingsScreen.route ->
                        NavigationTransitions.enterTransition(isEnteringFromRight = false)
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    MainRouteScreen.DevicesScreen.route,
                    MainRouteScreen.SettingsScreen.route ->
                        NavigationTransitions.exitTransition(isExitingToRight = false)
                    else -> null
                }
            }
        ) {
            HomeScreen(rootNavController)
        }

        composable(
            route = MainRouteScreen.DevicesScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    MainRouteScreen.HomeScreen.route ->
                        NavigationTransitions.enterTransition(isEnteringFromRight = true)
                    MainRouteScreen.SettingsScreen.route ->
                        NavigationTransitions.enterTransition(isEnteringFromRight = false)
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    MainRouteScreen.HomeScreen.route ->
                        NavigationTransitions.exitTransition(isExitingToRight = true)
                    MainRouteScreen.SettingsScreen.route ->
                        NavigationTransitions.exitTransition(isExitingToRight = false)
                    else -> null
                }
            }
        ) {
            DevicesScreen(rootNavController, searchQuery)
        }

        composable(
            route = MainRouteScreen.SettingsScreen.route,
            enterTransition = {
                when (initialState.destination.route) {
                    MainRouteScreen.HomeScreen.route,
                    MainRouteScreen.DevicesScreen.route ->
                        NavigationTransitions.enterTransition(isEnteringFromRight = true)
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    MainRouteScreen.HomeScreen.route,
                    MainRouteScreen.DevicesScreen.route ->
                        NavigationTransitions.exitTransition(isExitingToRight = true)
                    else -> null
                }
            }
        ) {
            SettingsScreen(rootNavController)
        }
    }
}
