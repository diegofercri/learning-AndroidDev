package net.azarquiel.andalusiabeaches.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.andalusiabeaches.view.MainActivity
import net.azarquiel.andalusiabeaches.viewmodel.MainViewModel
import net.azarquiel.andalusiabeaches.screens.MasterScreen
import net.azarquiel.andalusiabeaches.screens.CoastDetailScreen
import net.azarquiel.andalusiabeaches.screens.BeachesScreen


@Composable
fun AppNavigation(mainActivity: MainActivity
) {
    val navController = rememberNavController()
    val viewModel = MainViewModel(mainActivity)
    NavHost(navController = navController,
        startDestination = AppScreens.MasterScreen.route){
        composable(AppScreens.MasterScreen.route){
            MasterScreen(navController, viewModel)
        }
        composable(AppScreens.CoastDetailScreen.route) {
            CoastDetailScreen(navController, viewModel)
        }
        composable(AppScreens.BeachesScreen.route) {
            BeachesScreen(navController, viewModel)
        }
    }
}

sealed class AppScreens(val route: String) {
    object MasterScreen: AppScreens(route = "MasterScreen")
    object CoastDetailScreen: AppScreens(route = "CoastDetailScreen")
    object BeachesScreen: AppScreens(route = "BeachesScreen")
}
