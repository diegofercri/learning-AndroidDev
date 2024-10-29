package net.azarquiel.carrojpc
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import net.azarquiel.carrojpc.model.Producto
import java.util.ArrayList


@Composable
fun MainScreen(viewModel: MainViewModel) {
    DialogFab(viewModel)
    Scaffold(
        topBar = { CustomTopBar() },
        floatingActionButton = { CustomFAB(viewModel) },
        content = { padding ->
            CustomContent(padding,viewModel)
        }
    )
}


@Composable
fun CustomFAB(viewModel:  MainViewModel) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background,
        onClick = {
            viewModel.setDialog(true)
        }) {
        Text(
            fontSize = 24.sp,
            text = "+"
        )
    }
}


@Composable
fun DialogFab(viewModel: MainViewModel) {
    val context = LocalContext.current
    val openDialog = viewModel.openDialog.observeAsState(false)
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var isErrorEmptyNombre by remember { mutableStateOf(true) }
    var isErrorEmptyCantidad by remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            title = { Text(text = "Add Product") },
            text = {
                Column{
                    TextField(
                        modifier = Modifier.padding(bottom = 30.dp),
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            isErrorEmptyNombre = nombre.isEmpty() },
                        label = { Text("Nombre") },
                        placeholder = { Text("Manzanas") },
                        supportingText = {
                            if (isErrorEmptyNombre) { Text("No empty...") } },
                        isError = isErrorEmptyNombre, // No Empty sale en rojo
                        leadingIcon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Producto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true
                    )
                    TextField(
                        value = cantidad,
                        onValueChange = {
                            cantidad = it
                            isErrorEmptyCantidad= cantidad.isEmpty()},
                        label = { Text("Cantidad") },
                        supportingText = {
                            if (isErrorEmptyCantidad) {Text("No empty...") } },
                        isError = isErrorEmptyCantidad,
                        placeholder = { Text("2 kg") },
                        leadingIcon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Producto") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }},
            onDismissRequest = {  // Si pulsamos fuera cierra
                viewModel.setDialog(false)
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nombre.isEmpty() || cantidad.isEmpty()) {
                            Toast.makeText( context, "required fields", Toast.LENGTH_LONG).show()
                        }
                        else {
                            viewModel.addProducto(Producto(0,nombre, cantidad))
                            viewModel.setDialog(false)
                        }})
                { Text("Ok") }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.setDialog(false) })
                { Text("Cancel") }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        title = { Text(text = "Title") },
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
            .fillMaxSize()
            .padding(padding),
    )
    {
       val productos = viewModel.productos.observeAsState(ArrayList())
        LazyColumn {
            items(productos.value) { item ->
                MyCard(item)

            }

        }

    }
}


@Composable
fun MyCard(item: Producto) {
    var isComprado by remember { mutableStateOf(false) }
    Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp, 3.dp),
        /*
        .clickable {
            isComprado = !isComprado
        },
        */
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
        containerColor = if (isComprado) Color.LightGray else MaterialTheme.colorScheme.primary,
        contentColor = Color.White),
    ) {
        Column{
            Text(
                modifier = Modifier.
                    padding(16.dp)
                    .fillMaxWidth(),
                text = item.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            //Spacer(modifier = Modifier.padding(20.dp)) Para poder separar mas las column
            Text(
                modifier = Modifier.
                    padding(16.dp)
                    .fillMaxWidth(),
                text = item.cantidad,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End

            )

        }

    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(MainViewModel(LocalContext.current as MainActivity))
}

