package com.example.pueblosbonitos.navigation

import DetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pueblosbonitos.view.MainActivity
import com.example.pueblosbonitos.viewmodel.MainViewModel
import com.example.pueblosbonitos.screens.PueblosScreen
import com.example.pueblosbonitos.screens.MasterScreen
import com.example.pueblosbonitos.screens.WebScreen


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
        composable(AppScreens.PueblosScreen.route) {
          PueblosScreen(navController, viewModel)
        }
        composable(AppScreens.DetailScreen.route) {
            DetailScreen(navController, viewModel)
        }
        composable(AppScreens.WebScreen.route) {
            WebScreen(navController, viewModel)
        }
    }
}

sealed class AppScreens(val route: String) {
    object MasterScreen: AppScreens(route = "MasterScreen")
    object PueblosScreen: AppScreens(route = "PueblosScreen")
    object DetailScreen: AppScreens(route = "DetailScreen")
    object WebScreen: AppScreens(route = "WebScreen")
}
