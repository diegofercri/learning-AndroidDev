package net.azarquiel.gafasretrofitjpc.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.gafasretrofitjpc.screens.DetailScreen
import net.azarquiel.gafasretrofitjpc.screens.GafasScreen
import net.azarquiel.gafasretrofitjpc.screens.MarcasScreen
import net.azarquiel.gafasretrofitjpc.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppScreens.MarcasScreen.route){
        composable(AppScreens.MarcasScreen.route){
            MarcasScreen(navController, viewModel)
        }
        composable(AppScreens.GafasScreen.route){
            GafasScreen(navController, viewModel)
        }
        composable(AppScreens.DetailScreen.route){
            DetailScreen(navController, viewModel)
        }
    }
}
sealed class AppScreens(val route: String) {
    object MarcasScreen: AppScreens(route = "MarcasScreen")
    object GafasScreen: AppScreens(route = "GafasScreen")
    object DetailScreen: AppScreens(route = "DetailScreen")

}
