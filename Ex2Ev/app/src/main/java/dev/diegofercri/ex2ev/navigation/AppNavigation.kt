package dev.diegofercri.ex2ev.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.diegofercri.ex2ev.screens.*
import dev.diegofercri.ex2ev.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.MasterScreen.route
    ) {
        composable(AppScreens.MasterScreen.route) {
            MasterScreen(navController, viewModel)
        }
        composable(AppScreens.GafasScreen.route) {
            GafasScreen(navController, viewModel)
        }
        composable(AppScreens.DetailGafaScreen.route) {
            DetailGafasScreen(navController, viewModel)
        }
    }
}

sealed class AppScreens(val route: String) {
    object MasterScreen : AppScreens(route = "MasterScreen")
    object GafasScreen : AppScreens(route = "GafasScreen")
    object DetailGafaScreen : AppScreens(route = "DetailGafaScreen")
}