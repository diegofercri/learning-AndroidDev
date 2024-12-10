package com.example.pueblosbonitos.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pueblosbonitos.R
import com.example.pueblosbonitos.model.Comunidad
import com.example.pueblosbonitos.navigation.AppScreens
import com.example.pueblosbonitos.viewmodel.MainViewModel


@Composable
fun MasterScreen(navController: NavHostController, viewModel: MainViewModel) {
    Scaffold(
        topBar = { MasterTopBar(viewModel, navController) },
        content = { padding ->
            MasterContent(padding, navController, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterTopBar(viewModel: MainViewModel, navController: NavHostController) {
    TopAppBar(
        title = { Text(text = "Pueblos Bonitos") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
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
    val comunidades by viewModel.comunidades.observeAsState(emptyList())
    LazyColumn(
        modifier = Modifier
            .background(color = colorResource(id = R.color.azulo))
            .padding(padding)
    ) {
        items(comunidades) { comunidad ->
            CardComunidad(comunidad, navController)
        }
    }
}

@Composable
fun CardComunidad(comunidad: Comunidad, navController: NavHostController) {
    Card(
        modifier = Modifier.padding(2.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("comunidad", comunidad)
                navController.navigate(route = AppScreens.PueblosScreen.route)
            },
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.azulc)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = comunidad.nombreComunidad,
                color = colorResource(R.color.azulo),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
            )
        }
    }
}






