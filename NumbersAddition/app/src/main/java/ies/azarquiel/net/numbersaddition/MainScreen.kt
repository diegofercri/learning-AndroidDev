package net.azarquiel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ies.azarquiel.net.numbersaddition.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val targetNumber by viewModel.targetNumber.observeAsState(0)
    val firstNumber by viewModel.firstNumber.observeAsState("?")
    val secondNumber by viewModel.secondNumber.observeAsState("?")
    val rowColor by viewModel.rowColor.observeAsState(Color.White)
    val attempts by viewModel.attempts.observeAsState(0)
    val successes by viewModel.successes.observeAsState(0)
    val showDialog by viewModel.showDialog.observeAsState(false)

    if (showDialog) {
        ResultDialog(successes, viewModel::onDialogDismiss)
    }

    Scaffold(
        topBar = { CustomTopBar() },
        content = { padding ->
            CustomContent(
                padding,
                viewModel,
                targetNumber,
                firstNumber,
                secondNumber,
                rowColor,
                attempts,
                successes
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        title = { Text(text = "Addition Numbers") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}


@Composable
fun CustomContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    targetNumber: Int,
    firstNumber: String,
    secondNumber: String,
    rowColor: Color,
    attempts: Int,
    successes: Int
) {
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
            Text(
                text = "Aciertos: $successes / 10",
                fontSize = 24.sp,
            )
            Text(
                text = "Intentos: $attempts / 10",
                fontSize = 24.sp,
            )
        }
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(2F),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = targetNumber.toString(),
                fontSize = 140.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(2F)
                .background(rowColor),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$firstNumber",
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "+",
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$secondNumber",
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column (
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .weight(4F),
            verticalArrangement = Arrangement.Center
        ) {
            val botones = Array(3) { i -> Array(3) { j -> (i * 3 + j + 1) } }
            // botones = [[1,2,3],[4,5,6],[7,8,9]] tabla de 3x3 con los valores dentro
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
        Text(
            text = "$n",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ResultDialog(successes: Int, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Game Over")
        },
        text = {
            Text("Has acertado $successes de 10 intentos.")
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(MainViewModel())
}