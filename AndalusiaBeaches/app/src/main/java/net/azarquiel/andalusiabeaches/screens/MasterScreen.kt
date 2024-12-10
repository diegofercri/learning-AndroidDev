package net.azarquiel.andalusiabeaches.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun MasterScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { MasterTopBar() },
        content = { padding ->
            MasterContent(padding, navController, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterTopBar() {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(R.drawable.andalusia_logo),
                contentDescription = "logo",
                modifier = Modifier.size(150.dp)
            ) },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun MasterContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val coasts by viewModel.coasts.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .background(color = colorResource(id = R.color.azulo))
            .padding(padding)
    ) {
        items(coasts) { coast ->
            CardCoast(coast, navController)
        }
    }
}

@Composable
fun CardCoast(coast: Coast, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("coast", coast)
                navController.navigate(route = AppScreens.CoastDetailScreen.route)
            },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.azulc)),
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = coast.imagen,
                contentDescription = "Coast",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Text(
                text = coast.nombre,
                color = colorResource(R.color.azulo),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
        }
    }
}






