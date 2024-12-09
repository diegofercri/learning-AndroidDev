package net.azarquiel.acbroomjpc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.acbroomjpc.R
import net.azarquiel.acbroomjpc.model.Equipo
import net.azarquiel.acbroomjpc.model.Jugador
import net.azarquiel.acbroomjpc.model.JugadorWE
import net.azarquiel.acbroomjpc.viewmodel.MainViewModel


@Composable
fun MasterScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { MasterTopBar() },
        content = { padding ->
            MasterContent(padding, viewModel)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterTopBar() {
    TopAppBar(
        title = { Text(text = "ACB") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
fun MasterContent(padding: PaddingValues, viewModel: MainViewModel) {
    val jugadores = viewModel.jugadores.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),)
    {
        LazyColumn(
            Modifier.background(colorResource(R.color.azuloscuro)),
        ) {
            items(jugadores.value) { item ->
                JugadorCard(item)
            }
        }

    }
}

@Composable
fun JugadorCard(jugadorwe: JugadorWE) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 3.dp)
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azulclaro),
            contentColor = colorResource(R.color.azuloscuro)),
    ) {
        Row(
            modifier = Modifier.fillMaxHeight()
        ) {
            AsyncImage(
                model = jugadorwe.jugador.imagen,
                contentDescription = "Jugador  Image",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = jugadorwe.jugador.nombre,
                    color = colorResource(R.color.azulclaro),
                    modifier = Modifier
                        .background(colorResource(R.color.azuloscuro))
                        .fillMaxWidth()
                        ,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(Icons.Filled.ThumbUp, contentDescription = "Likes")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = jugadorwe.jugador.likes.toString(),
                        fontSize = 22.sp,
                        modifier = Modifier
                        .padding(end = 8.dp))
                }
                Text(
                    text = jugadorwe.jugador.pais,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
                Text(
                    text = jugadorwe.equipo.nombre,
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 8.dp, bottom = 8.dp),
                    textAlign = TextAlign.End,
                    )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    JugadorCard(JugadorWE(Jugador(1,1,"Perico","https://static.acb.com/media/PRO/00/00/50/55/07/0000505507_5-6_02.jpg","","España",0F,0,3), Equipo(1,"Real Madrid")))
}


