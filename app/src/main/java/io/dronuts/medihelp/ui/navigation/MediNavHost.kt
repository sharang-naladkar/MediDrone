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

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object LiveTracking : Screen("tracking/{incidentId}") {
        fun createRoute(id: String) = "tracking/$id"
    }
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
            HomeScreen(onStartTracking = { incidentId -> navController.navigate(Screen.LiveTracking.createRoute(incidentId)) })
        }
        composable(
            Screen.LiveTracking.route,
            arguments = listOf(navArgument("incidentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("incidentId") ?: ""
            // TODO: LiveTrackingScreen(incidentId = id)
        }
    }
}
