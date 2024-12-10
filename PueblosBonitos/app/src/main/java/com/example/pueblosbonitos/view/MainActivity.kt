package com.example.pueblosbonitos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pueblosbonitos.navigation.AppNavigation
import com.example.pueblosbonitos.ui.theme.PueblosBonitosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PueblosBonitosTheme {
                AppNavigation(this)
            }
        }
    }
}

