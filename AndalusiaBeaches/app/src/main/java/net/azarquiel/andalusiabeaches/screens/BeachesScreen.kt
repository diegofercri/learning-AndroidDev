package net.azarquiel.andalusiabeaches.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.andalusiabeaches.R
import net.azarquiel.andalusiabeaches.model.Coast
import net.azarquiel.andalusiabeaches.model.Beach
import net.azarquiel.andalusiabeaches.viewmodel.MainViewModel

@Composable
fun BeachesScreen(navController: NavHostController, viewModel: MainViewModel) {
    val coast = navController.previousBackStackEntry?.savedStateHandle?.get<Coast>("coast")
    Scaffold(
        topBar = { BeachesTopBar(navController,viewModel, coast) },
        floatingActionButton = { BeachesFAV(viewModel) },
        content = { padding ->
            BeachesContent(padding, navController, viewModel, coast)
        }
    )
}

@Composable
fun BeachesFAV(viewModel: MainViewModel) {
    val blue by viewModel.blue.observeAsState(R.color.gris)
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.background,
        onClick = {
        }) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "star",
            tint = colorResource(blue) ,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    viewModel.changeBlue()
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachesTopBar(
    navController: NavHostController,
    viewModel: MainViewModel,
    coast: Coast?
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
            ) {
                coast?.let { Text(text = it.name) }
            }
        }
        ,
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun BeachesContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel,
    coast: Coast?
) {
    val beaches by viewModel.beaches.observeAsState(emptyList())
    coast?.let {
        viewModel.getBeaches(coast.id)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            LazyColumn(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.azulo))
            ) {
                items(beaches) { beach ->
                    BeachCard(beach, navController, coast)
                }
            }
        }
    }
}

@Composable
fun BeachCard(beach: Beach, navController: NavHostController, coast: Coast) {
    Card(
        modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = colorResource(R.color.azulc)),
        ) {
            AsyncImage(
                model = beach.image,
                contentDescription = "Beach",
                modifier = Modifier.size(130.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth().height(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = beach.name,
                    color = colorResource(R.color.azulc),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .background(color = colorResource(R.color.azulo))
                )
                Text(
                    text = coast.name,
                    color = colorResource(R.color.azulo),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(Icons.Filled.Star,
                        contentDescription = "star",
                        tint = if (beach.blue == 1) colorResource(R.color.amarillo) else colorResource(R.color.gris),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}




