package com.example.calculadora_nomina.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = VerdeOscuro,
    onPrimary = FondoOscuro,
    primaryContainer = Color(0xFF164B3E),
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0xFF3F3728),
    background = FondoOscuro,
    surface = Color(0xFF1B2924),
    surfaceVariant = Color(0xFF253831)
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeNomina,
    onPrimary = Color.White,
    primaryContainer = VerdeClaro,
    onPrimaryContainer = Color(0xFF123D32),
    secondaryContainer = NaranjaSuave,
    background = FondoClaro,
    surface = Color.White,
    surfaceVariant = Color(0xFFE6F0EA)
)

@Composable
fun Calculadora_nominaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
