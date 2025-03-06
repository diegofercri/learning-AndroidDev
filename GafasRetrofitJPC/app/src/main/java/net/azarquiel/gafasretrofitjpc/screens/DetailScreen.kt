package net.azarquiel.gafasretrofitjpc.screens

import net.azarquiel.gafasretrofitjpc.R
import net.azarquiel.gafasretrofitjpc.model.Comentario
import net.azarquiel.gafasretrofitjpc.model.Gafa
import net.azarquiel.gafasretrofitjpc.viewmodel.MainViewModel
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.gafasretrofitjpc.model.Marca
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(navController: NavHostController, viewModel: MainViewModel) {
    val gafa = navController.previousBackStackEntry?.savedStateHandle?.get<Gafa>("gafa")
    val marca = navController.previousBackStackEntry?.savedStateHandle?.get<Marca>("marca")

    gafa?.let {
        DialogComment(viewModel, gafa)
        Scaffold(
            topBar = { DetailTopBar(viewModel) },
            floatingActionButton = { DetailGafa(viewModel, navController, gafa) },
            content = { padding ->
                DetailContent(padding, viewModel, gafa, navController, marca)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(viewModel: MainViewModel) {
    val usuario = viewModel.usuario.observeAsState()
    TopAppBar(
        title = { Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DETAIL",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = if (usuario.value != null) "  ${usuario.value!!.nick}" else "",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .weight(1f),
                textAlign = TextAlign.End
            )
        }
        },
        colors = topAppBarColors(
            containerColor = colorResource(R.color.rojo),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    gafa: Gafa,
    navController: NavHostController,
    marca: Marca?
) {
    gafa?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colorResource(R.color.rojoC)),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ShowGafa(gafa, viewModel, navController, marca)
        }
    }
}

@Composable
fun ShowGafa(gafa: Gafa, viewModel: MainViewModel, navController: NavHostController, marca: Marca?) {
    val comentarios = viewModel.getComentarios(gafa.id.toInt()).observeAsState(emptyList())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.rojoCM),
            contentColor = colorResource(R.color.black)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apigafas/img/gafas/${gafa.imagen}",
                contentDescription = gafa.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = gafa.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${gafa.precio} €",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    AsyncImage(
                        model = "http://www.ies-azarquiel.es/paco/apigafas/img/marcas/${marca?.imagen}",
                        contentDescription = "Marca",
                    )
                }
            }
        }
    }
    LazyColumn {
        items(comentarios.value) { comentario ->
            CardComentario(comentario, navController)
        }
    }
}


@Composable
fun CardComentario(comentario: Comentario, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.rojoComment),
            contentColor = colorResource(R.color.rojoC)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = comentario.fecha,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = comentario.nick,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comentario.comentario,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun DetailGafa(viewModel: MainViewModel,navController: NavHostController ,gafa: Gafa) {
    FloatingActionButton(
        containerColor = colorResource(R.color.rojo),
        contentColor = MaterialTheme.colorScheme.background,
        onClick = {
            viewModel.setDialogComentario(true)
        }) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = "Info",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogComment(viewModel: MainViewModel, gafa: Gafa) {
    val context = LocalContext.current
    var comment by remember { mutableStateOf("") }
    val usuarioLogeado = viewModel.usuario.observeAsState()
    val usuario = usuarioLogeado.value ?: return
    val fechaDate = Date()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val fecha: String = sdf.format(fechaDate)
    val comentario = Comentario(
        usuario = usuario.id ?: -1,
        gafa = gafa.id,
        fecha = fecha,
        comentario = comment
    )
    if (viewModel.openDialogComentario.observeAsState(false).value) {
        AlertDialog(
            title = { Text(text = "Añadir Comentario") },
            text = {
                Column {
                    TextField(
                        modifier = Modifier.padding(bottom = 30.dp),
                        value = comment,
                        onValueChange = { comment = it },
                        label = { Text("Comentario") },
                        placeholder = { Text("Escribe tu comentario aquí") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = false
                    )
                }
            },
            onDismissRequest = { viewModel.setDialogComentario(false) },
            confirmButton = {
                Button(
                    onClick = {
                        if (comment.isEmpty()) {
                            Toast.makeText(context, "Campos requeridos", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.saveComentario(gafa.id.toInt(), comentario)
                            comment = ""
                            viewModel.setDialogComentario(false)
                        }
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.setDialogComentario(false) }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}