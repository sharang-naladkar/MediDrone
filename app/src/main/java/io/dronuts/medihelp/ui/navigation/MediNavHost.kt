package io.dronuts.medihelp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.dronuts.medihelp.ui.screens.AuthScreen
import io.dronuts.medihelp.ui.screens.HomeScreen
import io.dronuts.medihelp.ui.screens.SplashScreen
import io.dronuts.medihelp.ui.screens.DispatchProgressScreen
import io.dronuts.medihelp.ui.screens.LiveTrackingScreen
import io.dronuts.medihelp.ui.screens.HistoryScreen
import io.dronuts.medihelp.ui.screens.ProfileScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Dispatch : Screen("dispatch/{incidentId}") {
        fun createRoute(id: String) = "dispatch/$id"
    }
    object LiveTracking : Screen("tracking/{incidentId}") {
        fun createRoute(id: String) = "tracking/$id"
    }
    object History : Screen("history")
    object Profile : Screen("profile")
}

@Composable
fun MediNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = { navController.navigate(Screen.Auth.route) })
        }
        composable(Screen.Auth.route) {
            AuthScreen(onAuthSuccess = { navController.navigate(Screen.Home.route) })
        }
        composable(Screen.Home.route) {
            HomeScreen(onStartTracking = { incidentId -> navController.navigate(Screen.Dispatch.createRoute(incidentId)) }, onOpenHistory = { navController.navigate(Screen.History.route) }, onOpenProfile = { navController.navigate(Screen.Profile.route) })
        }
        composable(
            Screen.Dispatch.route,
            arguments = listOf(navArgument("incidentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("incidentId")
            DispatchProgressScreen(incidentId = id, onReady = { readyId -> navController.navigate(Screen.LiveTracking.createRoute(readyId)) })
        }
        composable(
            Screen.LiveTracking.route,
            arguments = listOf(navArgument("incidentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("incidentId") ?: ""
            LiveTrackingScreen(incidentId = id)
        }
        composable(Screen.History.route) {
            HistoryScreen(onOpenIncident = { id -> navController.navigate(Screen.LiveTracking.createRoute(id)) })
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
