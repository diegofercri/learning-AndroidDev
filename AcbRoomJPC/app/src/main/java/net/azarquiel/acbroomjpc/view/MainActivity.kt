package net.azarquiel.acbroomjpc.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.acbroomjpc.navigation.AppNavigation
import net.azarquiel.acbroomjpc.ui.theme.AcbRoomJPCTheme
import net.azarquiel.acbroomjpc.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcbRoomJPCTheme {
                AppNavigation(this)
            }
        }
    }
}
