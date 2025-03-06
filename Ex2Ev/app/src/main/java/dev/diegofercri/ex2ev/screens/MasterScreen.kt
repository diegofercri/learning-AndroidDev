package dev.diegofercri.ex2ev.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.diegofercri.ex2ev.R
import dev.diegofercri.ex2ev.model.Marca
import dev.diegofercri.ex2ev.viewmodel.MainViewModel

@Composable
fun MasterScreen(navController: NavHostController, viewModel: MainViewModel) {
    val textFilterState = remember { mutableStateOf(TextFieldValue("")) }
    DialogLogin(viewModel)
    Scaffold(
        topBar = { MasterTopBar(textFilterState, viewModel) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Header Image
                HeaderImage()

                // Content with filtered cards
                MasterContent(padding, navController, viewModel, textFilterState)
            }
        }
    )
}

@Composable
fun HeaderImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp) // Altura fija para el header
    ) {
        Image(
            painter = painterResource(id = R.drawable.header), // Imagen local en res/drawable
            contentDescription = "Header Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit // Recorta la imagen para que se ajuste al espacio
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterTopBar(
    textFilterState: MutableState<TextFieldValue>,
    viewModel: MainViewModel
) {
    val usuario by viewModel.usuario.observeAsState()

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Título de la aplicación
                Text(
                    text = "GafasApp",
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // Campo de búsqueda
                SearchView(
                    state = textFilterState,
                    modifier = Modifier.weight(4f)
                )

                // Botón de login/logout
                if (usuario?.nick == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_account),
                        contentDescription = "Login",
                        modifier = Modifier
                            .weight(1f)
                            .size(35.dp)
                            .clickable {
                                viewModel.setDialogLogin(true)
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Logout",
                        modifier = Modifier
                            .weight(1f)
                            .size(35.dp)
                            .clickable {
                                viewModel.logout()
                            }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_800),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun MasterContent(
    padding: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel,
    textFilterState: MutableState<TextFieldValue>
) {
    val marcas by viewModel.marcas.observeAsState(listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyColumn {
            itemsIndexed(
                marcas.filter {
                    it.nombre.contains(textFilterState.value.text, ignoreCase = true)
                }
            ) { _, marca ->
                CardMarca(marca, navController, viewModel)
            }
        }
    }
}

@Composable
fun CardMarca(
    marca: Marca,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("marca", marca)
                navController.navigate("GafasScreen")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "http://www.ies-azarquiel.es/paco/apigafas/img/marcas/${marca.imagen}",
                contentDescription = "Marca",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier
) {
    TextField(
        value = state.value,
        onValueChange = { newValue ->
            state.value = newValue
        },
        modifier = modifier
            .padding(1.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(20.dp)),
        placeholder = {
            Text(
                text = "Buscar marcas...",
                color = Color.Gray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.Gray
            )
        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        )
    )
}

@Composable
fun DialogLogin(viewModel: MainViewModel) {
    val context = LocalContext.current
    val openDialog by viewModel.openDialogLogin.observeAsState(false)
    var nick by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    if (openDialog) {
        AlertDialog(
            title = { Text(text = "Login/Register") },
            text = {
                Column {
                    TextField(
                        value = nick,
                        onValueChange = { nick = it },
                        label = { Text("Nick") },
                        placeholder = { Text("Nick") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = "Nick") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true
                    )
                    TextField(
                        value = pass,
                        onValueChange = { pass = it },
                        label = { Text("Password") },
                        placeholder = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                }
            },
            onDismissRequest = { viewModel.setDialogLogin(false) },
            confirmButton = {
                Button(
                    onClick = {
                        if (nick.isEmpty() || pass.isEmpty()) {
                            Toast.makeText(context, "Campos requeridos", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.login(nick, pass)
                            nick = ""
                            pass = ""
                            viewModel.setDialogLogin(false)
                        }
                    }
                ) { Text("Ok") }
            },
            dismissButton = {
                Button(onClick = { viewModel.setDialogLogin(false) }) { Text("Cancelar") }
            }
        )
    }
}