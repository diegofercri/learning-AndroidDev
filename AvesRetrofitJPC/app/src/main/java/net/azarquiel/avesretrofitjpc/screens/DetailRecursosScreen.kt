package net.azarquiel.avesretrofitjpc.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import net.azarquiel.avesretrofitjpc.R
import net.azarquiel.avesretrofitjpc.model.Comentario
import net.azarquiel.avesretrofitjpc.model.Recurso
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailRecursosScreen(navController: NavHostController, viewModel: MainViewModel) {
    val recurso = navController.previousBackStackEntry?.savedStateHandle?.get<Recurso>("recurso")

    recurso?.let {
        DialogComment(viewModel, recurso)
        Scaffold(
            topBar = { DetailRecursosTopBar() },
            floatingActionButton = { DetailRecurso(viewModel, navController, recurso) },
            floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
            content = { padding ->
                DetailRecursosContent(padding, viewModel, recurso, navController)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRecursosTopBar() {
    TopAppBar(
        title = {
            Text(text = "AvesRetrofit",
                Modifier.padding(0.dp,0.dp,10.dp,0.dp)
            )},
        colors = topAppBarColors(
            containerColor = colorResource(id = R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailRecursosContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    recurso: Recurso,
    navController: NavHostController
) {
    recurso?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            ShowRecurso(recurso, viewModel, navController)
        }
    }
}

@Composable
fun ShowRecurso(recurso: Recurso, viewModel: MainViewModel, navController: NavHostController) {
    val comentarios = viewModel.getComentarios(recurso.id.toInt()).observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.purple_700)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = recurso.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.purple_700))
                .padding(vertical = 12.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        AsyncImage(
            model = recurso.url,
            contentDescription = recurso.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        LazyColumn {
            items(comentarios.value) { comentario ->
            CardComentario(comentario, navController)
        } }

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
            containerColor = colorResource(R.color.azul),
            contentColor = colorResource(R.color.purple_800)
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
fun DetailRecurso(viewModel: MainViewModel,navController: NavHostController ,recurso: Recurso) {
    FloatingActionButton(
        containerColor = colorResource(R.color.purple_700),
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
fun DialogComment(viewModel: MainViewModel, recurso: Recurso) {
    val context = LocalContext.current
    var comment by remember { mutableStateOf("") }
    val usuarioLogeado = viewModel.usuario.observeAsState()
    val usuario = usuarioLogeado.value ?: return
    val fechaDate = Date()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val fecha: String = sdf.format(fechaDate)
    val comentario = Comentario(
        usuario = usuario.id ?: -1,
        recurso = recurso.id.toString(),
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
                            viewModel.saveComentario(recurso.id.toInt(), comentario)
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