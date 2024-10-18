package net.azarquiel.blackjackjson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.azarquiel.blackjackjson.ui.theme.BlackJackJSONTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlackJackJSONTheme {
                MainScreen(MainViewModel(this))
            }
        }
    }
}
