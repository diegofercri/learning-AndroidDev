package net.azarquiel.famouspersonsgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.famouspersonsgame.ui.theme.FamousPersonsGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamousPersonsGameTheme() {
                MainScreen(MainViewModel(this))
            }
        }
    }
}