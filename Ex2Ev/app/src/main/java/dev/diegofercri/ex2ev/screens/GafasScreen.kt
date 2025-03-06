package dev.diegofercri.ex2ev.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.diegofercri.ex2ev.R
import dev.diegofercri.ex2ev.model.Gafa
import dev.diegofercri.ex2ev.model.Marca
import dev.diegofercri.ex2ev.viewmodel.MainViewModel

@Composable
fun GafasScreen(navController: NavHostController, viewModel: MainViewModel) {
    val marca = navController.previousBackStackEntry?.savedStateHandle?.get<Marca>("marca")
    marca?.let { nonNullMarca ->
        viewModel.getGafas(nonNullMarca.id)
    }
    Scaffold(
        topBar = { GafasTopBar(viewModel) },
        content = { padding ->
            GafasContent(padding, navController, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GafasTopBar(viewModel: MainViewModel) {
    val usuario by viewModel.usuario.observeAsState()
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Gafas",
                    Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                        .weight(3f)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun GafasContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val gafas by viewModel.gafas.observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        if (gafas.isEmpty()) {
            Text(
                text = "No hay gafas disponibles",
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.Gray
            )
        } else {
            LazyColumn {
                itemsIndexed(gafas) { _, gafa ->
                    CardGafa(gafa, navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun CardGafa(gafa: Gafa, navController: NavHostController, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("gafa", gafa)
                navController.navigate("DetailGafaScreen")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Fondo blanco
            contentColor = Color.Black // Texto negro
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apigafas/img/gafas/${gafa.imagen}",
                contentDescription = gafa.nombre,
                modifier = Modifier.size(140.dp, 80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = gafa.nombre,
                modifier = Modifier.weight(8f).fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}