package com.example.superheroes_parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.superheroes_parcial.ui.theme.Superheroes_parcialTheme
import com.ucaldas.aplicacion1.Nombres
import com.ucaldas.aplicacion1.Utils
import com.ucaldas.aplicacion1.listaNombres
import com.ucaldas.aplicacion1.ui.theme.HeroCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Superheroes_parcialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListaSuperHeroes(superheroes: List<Nombres>, onVerDetalle: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(superheroes, key = { it.id }) { heroe ->
            HeroCard(hero = heroe, onClick = { onVerDetalle(heroe.id) })
        }
    }
}

@Composable
fun DetalleSuperHeroe(hero: Nombres, onVolver: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            HeroCard(hero = hero, option = 1)
        }
        item {
            Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver a la lista")
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Utils.Rutas.LISTA,
        modifier = modifier
    ) {
        composable(Utils.Rutas.LISTA) {
            ListaSuperHeroes(
                superheroes = listaNombres,
                onVerDetalle = { id -> navController.navigate(Utils.Rutas.detalle(id)) }
            )
        }
        composable(
            route = Utils.Rutas.DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val hero = listaNombres.firstOrNull { it.id == id } ?: return@composable

            DetalleSuperHeroe(
                hero = hero,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
