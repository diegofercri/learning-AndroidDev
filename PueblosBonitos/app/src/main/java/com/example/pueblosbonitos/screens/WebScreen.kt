package com.example.pueblosbonitos.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.pueblosbonitos.model.PuebloWp
import com.example.pueblosbonitos.viewmodel.MainViewModel


@Composable
fun WebScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { WebTopBar() },
        content = { padding ->
            WebContent(padding, viewModel, navController)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebTopBar() {
    TopAppBar(
        title = { Text(text = "Pueblo Bonito") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
fun WebContent(padding: PaddingValues, viewModel: MainViewModel, navController: NavHostController) {
    val link = navController.previousBackStackEntry?.savedStateHandle?.get<String>("link")
    link?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),)
        {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(link)
                }
            }, update = {
                it.loadUrl(link)
            })
        }
    }
}
