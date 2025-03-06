package net.azarquiel.gafasretrofitjpc.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.gafasretrofitjpc.navigation.AppNavigation
import net.azarquiel.gafasretrofitjpc.ui.theme.GafasRetrofitJPCTheme
import net.azarquiel.gafasretrofitjpc.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = MainViewModel(this)
        setContent {
            GafasRetrofitJPCTheme {
                AppNavigation(viewModel)
            }
        }
    }
}


