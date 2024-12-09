package net.azarquiel.acbroomjpc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.acbroomjpc.viewmodel.MainViewModel
import net.azarquiel.acbroomjpc.screens.DetailScreen
import net.azarquiel.acbroomjpc.screens.MasterScreen
import net.azarquiel.acbroomjpc.view.MainActivity


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
        composable(AppScreens.DetailScreen.route) {
            DetailScreen(navController, viewModel)
        }
    }
}
sealed class AppScreens(val route: String) {
    object MasterScreen: AppScreens(route = "MasterScreen")
    object DetailScreen: AppScreens(route = "DetailScreen")
}
