import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pueblosbonitos.R
import com.example.pueblosbonitos.model.PuebloWp
import com.example.pueblosbonitos.navigation.AppScreens
import com.example.pueblosbonitos.viewmodel.MainViewModel

@Composable
fun DetailScreen(navController: NavHostController, viewModel: MainViewModel) {
    val pueblowp = navController.previousBackStackEntry?.savedStateHandle?.get<PuebloWp>("pueblowp")
    val nombrecomunidad = navController.previousBackStackEntry?.savedStateHandle?.get<String>("nombrecomunidad")
    Scaffold(
        topBar = { DetailTopBar(pueblowp) },
        content = { padding ->
            DetailContent(padding, viewModel, pueblowp, nombrecomunidad, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(pueblowp: PuebloWp?) {
    TopAppBar(
        title = { pueblowp?.let { Text(text = pueblowp.pueblo.nombrePueblo) }},
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun DetailContent(
    padding: PaddingValues,
    viewModel: MainViewModel,
    pueblowp: PuebloWp?,
    comunidad: String?,
    navController: NavHostController
) {
    pueblowp?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        )
        {
            ShowPueblo(pueblowp, comunidad, viewModel, navController)
        }
    }
}

@Composable
fun ShowPueblo(
    pueblowp: PuebloWp,
    comunidad: String?,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    var fav by remember { mutableIntStateOf(pueblowp.pueblo.fav) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = pueblowp.pueblo.imagen,
            contentDescription = "Pueblo",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = pueblowp.pueblo.nombrePueblo,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.azulo))
                .padding(vertical = 20.dp),
            color = colorResource(R.color.azulc),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,

        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = if (comunidad != null)
                "${pueblowp.provincia.nombreProvincia} ($comunidad)"
            else
                "$pueblowp.provincia.nombreProvincia",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            color = colorResource(R.color.azulo),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,

        )
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set("link", pueblowp.pueblo.link)
                    navController.navigate(route = AppScreens.WebScreen.route)
                },
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(Icons.Filled.Home, "home",
                tint = colorResource(R.color.azulo),
                modifier = Modifier.size(28.dp))
            Text(
                text = " Site...",
                color = colorResource(R.color.azulo),
                fontSize = 22.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "star",
                tint = if (fav == 1) colorResource(R.color.amarillo) else colorResource(
                    R.color.gris),
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        if (fav==0) fav=1 else fav=0
                        viewModel.changeFavPueblo(pueblowp.pueblo)

                    }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ShowPueblo(
//        PuebloWp(Pueblo(1,"Agulo", provincia = 1,), Provincia(1,"La Gomera", comunidad = 1)),
//        "Andalucia"
//    )
//}