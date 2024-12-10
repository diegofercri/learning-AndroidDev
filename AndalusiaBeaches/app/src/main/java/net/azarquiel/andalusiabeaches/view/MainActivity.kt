package net.azarquiel.andalusiabeaches.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.andalusiabeaches.navigation.AppNavigation
import net.azarquiel.andalusiabeaches.ui.theme.AndalusiaBeachesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndalusiaBeachesTheme {
                AppNavigation(this)
            }
        }
    }
}

