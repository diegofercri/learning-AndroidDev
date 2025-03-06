package net.azarquiel.avesretrofitjpc.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import net.azarquiel.avesretrofitjpc.R
import net.azarquiel.avesretrofitjpc.model.Zona
import net.azarquiel.avesretrofitjpc.viewmodel.MainViewModel


@Composable
fun MasterScreen(navController: NavHostController, viewModel: MainViewModel) {
    val textFilterState = remember { mutableStateOf(TextFieldValue("")) }
    DialogLogin(viewModel)
    Scaffold(
        topBar = { MasterTopBar(textFilterState, viewModel) },
        content = { padding ->
            MasterContent(padding, navController, viewModel, textFilterState)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterTopBar(textFilterState: MutableState<TextFieldValue>, viewModel: MainViewModel) {
   val usuario = viewModel.usuario.observeAsState()
    TopAppBar(
        title = { Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "AvesRetrofit" + if (usuario.value?.nick == null){" "} else{" - "+usuario.value?.nick},
                Modifier.padding(0.dp,0.dp,10.dp,0.dp)
                    .weight(3f))
            if (usuario.value?.nick == null){
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
            } else{
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

        }},
        colors = topAppBarColors(
            containerColor = colorResource(R.color.purple_800),
            titleContentColor = MaterialTheme.colorScheme.background
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
    val zonas = viewModel.zonas.observeAsState(listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    )
    {
        LazyColumn {
            itemsIndexed(zonas.value.filter { it.nombre.contains(textFilterState.value.text, ignoreCase = true) })
            { i, categoria ->
                CardZona(categoria, navController, viewModel)
            }
        }
    }
}

@Composable
fun CardZona(zona: Zona, navController: NavHostController, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set("zona", zona)
                navController.navigate("DetailScreen")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.azul),
            contentColor = Color.Black
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = zona.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = zona.localizacion,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun DialogLogin(viewModel: MainViewModel) {
    val context = LocalContext.current
    val openDialog = viewModel.openDialogLogin.observeAsState(false)
    var nick by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    if (openDialog.value) {
        AlertDialog(
            title = { Text(text = "Login/Register") },
            text = {
                Column{
                    TextField(
                        modifier = Modifier.padding(bottom = 30.dp),
                        value = nick,
                        onValueChange = {
                            nick = it
                        },
                        label = { Text("Nick") },
                        placeholder = { Text("Nick") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = "Producto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true
                    )
                    TextField(
                        value = pass,
                        onValueChange = {
                            pass = it
                        },
                        label = { Text("Password") },
                        placeholder = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                }},
            onDismissRequest = {  // Si pulsamos fuera cierra
                viewModel.setDialogLogin(false)
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nick.isEmpty() || pass.isEmpty()) {
                            Toast.makeText( context, "required fields", Toast.LENGTH_LONG).show()
                        }
                        else {
                            viewModel.login(nick,pass)
                            nick = ""
                            pass = ""
                            viewModel.setDialogLogin(false)
                        }})
                { Text("Ok") }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.setDialogLogin(false) })
                { Text("Cancel") }
            }
        )
    }
}




