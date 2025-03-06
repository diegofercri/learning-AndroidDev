package dev.diegofercri.ex2ev.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import dev.diegofercri.ex2ev.viewmodel.MainViewModel

@Composable
fun DetailGafasScreen(navController: NavHostController, viewModel: MainViewModel) {
    val gafa = navController.previousBackStackEntry?.savedStateHandle?.get<Gafa>("gafa")

    gafa?.let {
        Scaffold(
            topBar = { DetailGafasTopBar() },
            floatingActionButton = { DetailGafaFab(viewModel, navController, gafa) },
            floatingActionButtonPosition = FabPosition.Center,
            content = { padding ->
                DetailGafasContent(padding, gafa)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailGafasTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Detalle de Gafa",
                Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailGafasContent(
    padding: PaddingValues,
    gafa: Gafa
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowGafa(gafa)
    }
}

@Composable
fun ShowGafa(gafa: Gafa) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de la gafa
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apigafas/img/gafas/${gafa.imagen}",
                contentDescription = gafa.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre de la gafa
            Text(
                text = gafa.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Precio y logo de la marca
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Precio
                Text(
                    text = "${String.format("%.2f", gafa.precio.toDouble())} €",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.purple_700)
                )

                // Logo de la marca
                AsyncImage(
                    model = "http://www.ies-azarquiel.es/paco/apigafas/img/marcas/${gafa.marca}.jpg",
                    contentDescription = "Logo de la marca",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun DetailGafaFab(viewModel: MainViewModel, navController: NavHostController, gafa: Gafa) {
    FloatingActionButton(
        containerColor = colorResource(R.color.purple_700),
        contentColor = MaterialTheme.colorScheme.background,
        onClick = {
            viewModel.setDialogComentario(true)
        }
    ) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = "Añadir Comentario"
        )
    }
}