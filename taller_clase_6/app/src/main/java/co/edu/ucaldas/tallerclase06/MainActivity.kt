package co.edu.ucaldas.tallerclase06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val estadoLista = rememberLazyListState()
    var busqueda by rememberSaveable { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            ListaScreen(
                busqueda = busqueda,
                alBuscar = { busqueda = it },
                estadoLista = estadoLista,
                abrirDetalle = { id ->
                    navController.navigate("detalle/$id") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = "detalle/{elementoId}",
            arguments = listOf(navArgument("elementoId") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("elementoId")
            DetalleScreen(id = id, volver = { navController.popBackStack() })
        }
    }
}

@Composable
fun ListaScreen(
    busqueda: String,
    alBuscar: (String) -> Unit,
    estadoLista: LazyListState,
    abrirDetalle: (Int) -> Unit
) {
    val filtrados = elementos.filter {
        it.titulo.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Apuntes de móviles", style = MaterialTheme.typography.headlineMedium)
        Text("Temas para repasar", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = busqueda,
            onValueChange = alBuscar,
            label = { Text("Buscar por título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Resultados: ${filtrados.size} de ${elementos.size}")

        if (filtrados.isEmpty()) {
            Text("No hay temas con ese título.")
        } else {
            LazyColumn(
                state = estadoLista,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = filtrados, key = { it.id }) { elemento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { abrirDetalle(elemento.id) }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconoCategoria(elemento.categoria)
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(elemento.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(elemento.categoria, style = MaterialTheme.typography.labelLarge)
                                Text(elemento.descripcionCorta)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleScreen(id: Int?, volver: () -> Unit) {
    val elemento = elementos.firstOrNull { it.id == id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (elemento == null) {
            Text("No se encontró el tema.", style = MaterialTheme.typography.headlineSmall)
        } else {
            IconoCategoria(elemento.categoria)
            Text(elemento.titulo, style = MaterialTheme.typography.headlineMedium)
            Text("ID: ${elemento.id}")
            Text("Categoría: ${elemento.categoria}")
            Text(elemento.descripcionCorta, style = MaterialTheme.typography.titleMedium)
            Text(elemento.descripcionLarga)
        }
        Button(onClick = volver) {
            Text("Volver")
        }
    }
}

@Composable
fun IconoCategoria(categoria: String) {
    val icono = when (categoria) {
        "Compose" -> Icons.Default.Settings
        "Navegación" -> Icons.AutoMirrored.Filled.ArrowForward
        else -> Icons.Default.Info
    }
    Icon(
        imageVector = icono,
        contentDescription = categoria,
        modifier = Modifier.size(28.dp)
    )
}
