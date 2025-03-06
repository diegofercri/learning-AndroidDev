package net.azarquiel.avesretrofitjpc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import net.azarquiel.avesretrofitjpc.R
import net.azarquiel.avesretrofitjpc.model.Zona
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel


@Composable
fun DetailScreen(navController: NavHostController, viewModel: MainViewModel) {
    val zona = navController.previousBackStackEntry?.savedStateHandle?.get<Zona>("zona")
    zona?.let {
        Scaffold(
            topBar = { DetailTopBar(zona) },
            floatingActionButton = { DetailZona(viewModel, navController, zona) },
            floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
            content = { padding ->
                DetailContent(padding, viewModel, zona, navController)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(zona: Zona?) {
    TopAppBar(
        title = {
            Text(text = "Zona",
                Modifier.padding(0.dp,0.dp,10.dp,0.dp)
                   )},
        colors = topAppBarColors(
            containerColor = colorResource(id = R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    zona: Zona?,
    navController: NavHostController
) {
    zona?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            ShowZona(zona, viewModel, navController)
        }
    }
}

@Composable
fun ShowZona(
    zona: Zona,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.azul)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = zona.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.purple_700))
                .padding(vertical = 12.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = zona.localizacion,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Text(
            text = "Formaciones principales",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.purple_700))
                .padding(vertical = 6.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = zona.formaciones_principales,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
        Text(
            text = "Presentación",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.purple_700))
                .padding(vertical = 6.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = zona.presentacion,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
    }
}
@Composable
fun DetailZona(viewModel: MainViewModel,navController: NavHostController ,zona: Zona) {
    FloatingActionButton(
        containerColor = colorResource(R.color.purple_700),
        contentColor = MaterialTheme.colorScheme.background,
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set("zona", zona)
            navController.navigate("RecursosScreen")
        }) {
        Icon(
            Icons.Filled.Info,
            contentDescription = "Info",
        )
    }
}

