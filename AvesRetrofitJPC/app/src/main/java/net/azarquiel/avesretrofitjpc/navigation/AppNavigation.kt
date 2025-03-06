package net.azarquiel.avesretrofitjpc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.avesretrofitjpc.screens.DetailRecursosScreen
import net.azarquiel.avesretrofitjpc.screens.DetailScreen
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel
import net.azarquiel.avesretrofitjpc.screens.MasterScreen
import net.azarquiel.avesretrofitjpc.screens.RecursosScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppScreens.MasterScreen.route){
        composable(AppScreens.MasterScreen.route){
            MasterScreen(navController, viewModel)
        }
        composable(AppScreens.DetailScreen.route){
            DetailScreen(navController, viewModel)
        }
        composable(AppScreens.RecursosScreen.route){
            RecursosScreen(navController, viewModel)
        }
        composable(AppScreens.DetailRecursosScreen.route){
            DetailRecursosScreen(navController, viewModel)
        }
    }
}
sealed class AppScreens(val route: String) {
    object MasterScreen: AppScreens(route = "MasterScreen")
    object DetailScreen: AppScreens(route = "DetailScreen")
    object RecursosScreen: AppScreens(route = "RecursosScreen")
    object DetailRecursosScreen: AppScreens(route = "DetailRecursosScreen")
}
