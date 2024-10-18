package net.azarquiel.blackjackjson
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.azarquiel.blackjackjson.model.Carta


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
    val titulo by viewModel.titulo.observeAsState("")
    TopAppBar(
        title = {
            Text(text = "Black Jack $titulo")
                },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
fun CustomContent(padding: PaddingValues, viewModel: MainViewModel) {
   Box (
       modifier = Modifier
           .fillMaxSize()
           .padding(padding),

   ){
       Image(
           painter = painterResource(id = R.drawable.mesadepoker),
           contentDescription = "Fondo",
           modifier = Modifier.fillMaxSize(),
           contentScale = ContentScale.FillBounds
       )

   }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.SpaceEvenly,
        content = {
            MainContent(viewModel)
        }
    )
}


@Composable
fun MainContent(viewModel: MainViewModel) {
    val cartas = viewModel.cartas
    // a pintarrrr
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ){
        LazyRow(
            
        ) {
            items(cartas) { item ->
                CartaCard(carta = item, viewModel = viewModel)
            }
        }

    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ){
        Image(
                painter = painterResource(id = R.drawable.mazo),
                contentDescription = "mazo",
                modifier = Modifier.width(100.dp).height(180.dp)
                .clickable {
                    viewModel.sacaCarta()
                }

        )
    }
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ){
        Button(
            onClick = {viewModel.nextPlayer() },
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(
                text = "Stop",
                fontSize = 20.sp
            )
        }
    }

}

@Composable
fun CartaCard(carta: Carta, viewModel: MainViewModel) {
    val context = LocalContext.current
    val id = context.resources.getIdentifier("${viewModel.palos[carta.palo]}${carta.numero}", "drawable", context.packageName)
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),

    )
    {
        Image(
        painter = painterResource(id),
        contentDescription = "carta",
        modifier = Modifier.width(100.dp).height(140.dp),
        contentScale = ContentScale.Fit

        )
    }



}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(MainViewModel(MainActivity()))
}
