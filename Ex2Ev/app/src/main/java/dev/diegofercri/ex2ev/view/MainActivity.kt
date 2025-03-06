package dev.diegofercri.ex2ev.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.diegofercri.ex2ev.navigation.AppNavigation
import dev.diegofercri.ex2ev.ui.theme.Ex2EvTheme
import dev.diegofercri.ex2ev.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = MainViewModel(this)
        setContent {
            Ex2EvTheme() {
                AppNavigation(viewModel)
            }
        }
    }
}