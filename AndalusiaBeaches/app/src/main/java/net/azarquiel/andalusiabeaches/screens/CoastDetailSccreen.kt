package net.azarquiel.andalusiabeaches.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.andalusiabeaches.R
import net.azarquiel.andalusiabeaches.model.Coast
import net.azarquiel.andalusiabeaches.navigation.AppScreens
import net.azarquiel.andalusiabeaches.viewmodel.MainViewModel

@Composable
fun CoastDetailScreen(navController: NavHostController, viewModel: MainViewModel) {
    val coast = navController.previousBackStackEntry?.savedStateHandle?.get<Coast>("coast")
    Scaffold(
        topBar = { DetailTopBar(coast) },
        content = { padding ->
            DetailContent(padding, viewModel, coast, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(coast: Coast?) {
    TopAppBar(
        title = { coast?.let { Text(text = coast.name) }},
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    coast: Coast?,
    navController: NavHostController
) {
    coast?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            ShowCoast(coast, viewModel, navController)
        }
    }
}

@Composable
fun ShowCoast(
    coast: Coast,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = coast.image,
            contentDescription = "Coast",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = coast.name,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.azulo))
                .padding(vertical = 40.dp),
            color = colorResource(R.color.azulc),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            )
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("coast", coast)
                    navController.navigate(route = AppScreens.BeachesScreen.route)
                },
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(Icons.Filled.Home, "home",
                tint = colorResource(R.color.azulo),
                modifier = Modifier.size(28.dp))
            Text(
                text = " Site...",
                color = colorResource(R.color.azulo),
                fontSize = 22.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "star",
            )
        }
    }
}