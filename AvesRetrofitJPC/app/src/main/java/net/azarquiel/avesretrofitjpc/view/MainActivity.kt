package net.azarquiel.avesretrofitjpc.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.avesretrofitjpc.navigation.AppNavigation
import net.azarquiel.avesretrofitjpc.ui.theme.AvesRetrofitJPCTheme
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = MainViewModel(this)
        setContent {
            AvesRetrofitJPCTheme {
                AppNavigation(viewModel)
            }
        }
    }
}
