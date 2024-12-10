package net.azarquiel.andalusiabeaches.screens

import android.widget.SearchView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
        topBar = { BeachesTopBar(coast) },
        floatingActionButton = { BlueBeaches(viewModel) },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = { padding ->
            BeachesContent(padding, viewModel, coast)
        }
    )
}

@Composable
fun BlueBeaches(viewModel: MainViewModel) {
    val blue by viewModel.blue.observeAsState(R.drawable.not_blue_beach)
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondary,
        onClick = {
            viewModel.changeBlue()
        }) {
        Image(
            painter = painterResource(blue),
            contentDescription = "Blue Button",
            modifier = Modifier.size(50.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachesTopBar(
    coast: Coast?
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
            ) {
                coast?.let { Text(text = it.nombre) }
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
    viewModel: MainViewModel,
    coast: Coast?,
) {
    val beaches by viewModel.beaches.observeAsState(emptyList())
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
    coast?.let {
        viewModel.getBeaches(coast.id)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            SearchView(textState)
            LazyColumn(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.azulc))
            ) {
                items(beaches.filter { it.nombre.contains(textState.value.text, ignoreCase = true) }) { beach ->
                    BeachCard(beach, coast)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    state: MutableState<TextFieldValue>
) {
    TextField(
        value = state.value,
        onValueChange = { value-> state.value = value },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(20.dp)),
        placeholder = {
            Text(text = "Buscar Playa...")
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
fun BeachCard(beach: Beach, coast: Coast) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.azulo)),
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = beach.imagen,
                contentDescription = "Beach",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.azulo))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = beach.nombre,
                    color = colorResource(R.color.azulc),
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
                if (beach.azul == 1) {
                    Image(
                        painter = painterResource(R.drawable.blue_beach),
                        contentDescription = "Blue Beach",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 8.dp)
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}




