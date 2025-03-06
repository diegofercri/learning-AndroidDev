package net.azarquiel.avesretrofitjpc.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
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
import net.azarquiel.avesretrofitjpc.R
import net.azarquiel.avesretrofitjpc.model.Recurso
import net.azarquiel.avesretrofitjpc.model.Zona
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel

@Composable
fun RecursosScreen(navController: NavHostController, viewModel: MainViewModel) {
    val zona = navController.previousBackStackEntry?.savedStateHandle?.get<Zona>("zona")
    zona?.let { nonNullZona ->
        viewModel.getRecursos(nonNullZona.id)
    }
    Scaffold(
        topBar = { RecursosTopBar(viewModel) },
        content = { padding ->
            RecursosContent(padding, navController, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecursosTopBar(viewModel: MainViewModel) {
    val usuario = viewModel.usuario.observeAsState()
    TopAppBar(
        title = { Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Recursos",
                Modifier.padding(0.dp,0.dp,10.dp,0.dp)
                    .weight(3f))

        }},
        colors = topAppBarColors(
            containerColor = colorResource(R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun RecursosContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val recursos = viewModel.recursos.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    )
    {
        LazyColumn {
            itemsIndexed(recursos.value)
            { i, recurso ->
                CardRecurso(recurso, navController, viewModel)
            }
        }
    }
}

@Composable
fun CardRecurso(recurso: Recurso, navController: NavHostController, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("recurso", recurso)
                navController.navigate("DetailRecursosScreen")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azul),
            contentColor = Color.Black
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
                model = recurso.url,
                contentDescription = recurso.titulo,
                modifier = Modifier
                    .size(140.dp,80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = recurso.titulo,
                modifier = Modifier.weight(8f).fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.purple_700),
                textAlign = TextAlign.Center
            )
        }
    }
}