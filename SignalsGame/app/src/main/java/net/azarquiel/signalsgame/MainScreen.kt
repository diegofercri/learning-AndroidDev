package net.azarquiel.signalsgame

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = { CustomTopBar(viewModel) },
        content = { padding ->
            CustomContent(padding, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(viewModel: MainViewModel) {
    TopAppBar(
        title = {
            Text(text = "Signals Game")
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
fun CustomContent(padding: PaddingValues, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Aciertos: 0",
                fontSize = 24.sp,)
            Text(text = "Intentos: 0",
                fontSize = 24.sp,)
        }
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(4F),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "lorem ipsum dolor sit amet",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
        Column (
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(4F),
            verticalArrangement = Arrangement.Center
        ) {
            val botones = Array(2) { i -> Array(2) { j -> (i * 2 + j + 1) } }
            // botones = [[1,2],[4,5]] tabla de 2x2 con los valores dentro
            botones.forEach {fila->
                Row(
                    modifier = Modifier
                        .padding(1.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    fila.forEach { item ->
                        Boton(item, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Boton(n: Int, viewModel: MainViewModel) {
    Button(
        onClick = { viewModel.onClick(n) },
        contentPadding = PaddingValues(30.dp, 10.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Image(
            painter = painterResource(
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(MainViewModel(MainActivity()))
}