package com.example.calculadora_nomina

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class PantallaNominaTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun salarioVacioYLimpiar() {
        compose.onNodeWithText("Calcular").performScrollTo().performClick()
        compose.onNodeWithText("Ingrese un salario válido").assertIsDisplayed()
        compose.onNodeWithText("Salario básico mensual").performTextInput("8000000")
        compose.onNodeWithText("Calcular").performScrollTo().performClick()
        compose.onNodeWithText("7.280.000", substring = true).assertExists()
        compose.onNodeWithText("Limpiar").performScrollTo().performClick()
        compose.onNodeWithText("7.280.000", substring = true).assertDoesNotExist()
        compose.onNodeWithText("Ingrese un salario válido").assertDoesNotExist()
    }

    @Test fun rechazaSalarioMenorAlMinimo() {
        compose.onNodeWithText("Salario básico mensual").performTextInput("1000000")
        compose.onNodeWithText("Calcular").performScrollTo().performClick()
        compose.onNodeWithText("El salario no puede ser inferior al mínimo", substring = true).assertExists()
    }

    @Test fun limitaLasHorasExtra() {
        compose.onNodeWithText("Salario básico mensual").performTextInput("2000000")
        compose.onNodeWithText("Horas extra diurnas").performTextInput("30")
        compose.onNodeWithText("Horas extra nocturnas").performTextInput("20")
        compose.onNodeWithText("Calcular").performScrollTo().performClick()
        compose.onNodeWithText("El total de horas extra no puede superar 48 en el mes").assertExists()
    }
}
