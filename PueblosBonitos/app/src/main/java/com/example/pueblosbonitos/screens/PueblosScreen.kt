package com.example.pueblosbonitos.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pueblosbonitos.R
import com.example.pueblosbonitos.model.Comunidad
import com.example.pueblosbonitos.model.PuebloWp
import com.example.pueblosbonitos.navigation.AppScreens
import com.example.pueblosbonitos.viewmodel.MainViewModel

@Composable
fun PueblosScreen(navController: NavHostController, viewModel: MainViewModel) {
    val comunidad = navController.previousBackStackEntry?.savedStateHandle?.get<Comunidad>("comunidad")
    Scaffold(
        topBar = { PueblosTopBar(navController,viewModel, comunidad) },
        floatingActionButton = { PueblosFAB(viewModel) },
        content = { padding ->
            PueblosContent(padding, navController, viewModel, comunidad)
        }
    )
}

@Composable
fun PueblosFAB(viewModel: MainViewModel) {
    val colorFav by viewModel.colorFav.observeAsState(R.color.gris)
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.background,
        onClick = {
        }) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "star",
            tint = colorResource(colorFav) ,
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    viewModel.changeFav()
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PueblosTopBar(
    navController: NavHostController,
    viewModel: MainViewModel,
    comunidad: Comunidad?
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
            ) {
                comunidad?.let { Text(text = it.nombreComunidad) }
            }
        }
        ,
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun PueblosContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel,
    comunidad: Comunidad?
) {
    val pueblos by viewModel.pueblos.observeAsState(emptyList())
    comunidad?.let {
        viewModel.getPueblos(comunidad.idComunidad)

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
                items(pueblos) { pueblo ->
                    CardPueblo(pueblo, navController, comunidad)
                }
            }
        }
    }
}

@Composable
fun CardPueblo(pueblowp: PuebloWp, navController: NavHostController, comunidad: Comunidad) {
    Card(
        modifier = Modifier.padding(2.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("pueblowp", pueblowp)
                navController.currentBackStackEntry?.savedStateHandle?.set("nombrecomunidad", comunidad.nombreComunidad)
                navController.navigate(route = AppScreens.DetailScreen.route)
            },
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = colorResource(R.color.azulc)),
        ) {
            AsyncImage(
                model = pueblowp.pueblo.imagen,
                contentDescription = "Pueblo",
                modifier = Modifier.size(130.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth().height(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = pueblowp.pueblo.nombrePueblo,
                    color = colorResource(R.color.azulc),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .background(color = colorResource(R.color.azulo))
                )
                Text(
                    text = pueblowp.provincia.nombreProvincia,
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
                        tint = if (pueblowp.pueblo.fav == 1) colorResource(R.color.amarillo) else colorResource(R.color.gris),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CardPueblo(
//        PuebloWp(Pueblo(1,"Agulo", provincia = 1,), Provincia(1,"La Gomera", comunidad = 1))
//    )
//}




