package com.ucaldas.aplicacion1.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ucaldas.aplicacion1.Nombres

@Composable
fun HeroImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxWidth().height(220.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HeroInfo(hero: Nombres, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("ID: ${hero.id}", style = MaterialTheme.typography.labelMedium)
        Text(
            hero.nombre,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            hero.descripcion,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            "Superpoderes: ${hero.superPoderes.joinToString()}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun HeroInfoDetail(hero: Nombres, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        hero.nombre.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Column {
                    Text("Héroe #${hero.id}", style = MaterialTheme.typography.labelLarge)
                    Text(hero.nombre, style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        Text("Descripción", style = MaterialTheme.typography.titleMedium)
        Text(hero.descripcion, style = MaterialTheme.typography.bodyLarge)

        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Métricas", style = MaterialTheme.typography.titleMedium)
                Text("Cantidad de superpoderes: ${hero.superPoderes.size}")
                Text("Perfil: ${heroProfile(hero.superPoderes)}")
            }
        }

        Text("Superpoderes", style = MaterialTheme.typography.titleMedium)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            hero.superPoderes.forEach { poder ->
                AssistChip(onClick = {}, label = { Text(poder) })
            }
        }

        Text("Resumen táctico", style = MaterialTheme.typography.titleMedium)
        Text(
            "${hero.nombre} tiene un perfil ${heroProfile(hero.superPoderes)} y " +
                "${hero.superPoderes.size} superpoderes registrados.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun heroProfile(superPoderes: List<String>): String = when {
    superPoderes.any { it.contains("Inteligencia", ignoreCase = true) } &&
        superPoderes.any { it.contains("Combate", ignoreCase = true) } -> "Estratega"
    superPoderes.any { it.contains("Energia", ignoreCase = true) } -> "Cosmico"
    superPoderes.any { it.contains("velocidad", ignoreCase = true) } -> "Velocista"
    else -> "Titan"
}

@Composable
fun HeroCard(
    hero: Nombres,
    onClick: (() -> Unit)? = null,
    option: Int = 0,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().then(
            if (onClick != null) Modifier.clickable { onClick() } else Modifier
        )
    ) {
        HeroImage(imageUrl = hero.imagenUrl, contentDescription = hero.nombre)
        if (option == 0) {
            HeroInfo(hero = hero)
        } else {
            HeroInfoDetail(hero = hero)
        }
    }
}
