package net.azarquiel.famouspersonsgame

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = { CustomTopBar() },
        content = { padding ->
            CustomContent(padding, viewModel)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        title = { Text(text = "Famous Persons Game") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun CustomContent(padding: PaddingValues, viewModel: MainViewModel) {
    // Observamos los datos del ViewModel
    val fivePersonIds by viewModel.fivePersonIds.observeAsState(emptyList())
    val fivePersonNames by viewModel.fivePersonNames.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        fivePersonIds.indices.forEach { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón para el ID de la persona
                Button(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(3.dp),
                    onClick = {
                        // Lógica para el botón de ID (personalizar según la funcionalidad)
                        Log.d("IDButton", "Clicked ID: ${fivePersonIds[index]}")
                        viewModel.pressedId(fivePersonIds[index])
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    RoundedImage(imageId = fivePersonIds[index])
                    Text(text = "ID: ${fivePersonIds[index]}")
                }

                // Botón para el Nombre de la persona
                Button(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .padding(3.dp),
                    onClick = {
                        // Lógica para el botón de nombre (personalizar según la funcionalidad)
                        Log.d("NameButton", "Clicked Name: ${fivePersonNames[index]}")
                        viewModel.pressedName(fivePersonNames[index])
                    },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Text(text = "${fivePersonNames[index]}")
                }
            }
        }
    }
}

@Composable
fun RoundedImage(imageId: String) {
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier(imageId, "drawable", context.packageName)

    if (imageResId != 0) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajusta la imagen para cubrir sin dejar espacio en blanco
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        )
    }
}
