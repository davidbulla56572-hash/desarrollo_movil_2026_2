package com.example.calculadora_nomina

import androidx.compose.ui.text.AnnotatedString
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatoEntradaTest {
    @Test fun salarioSeLeeConSeparadoresSinCambiarElNumeroEscrito() {
        val original = AnnotatedString("1750905")
        val texto = SeparadorMiles.filter(original)
        assertEquals("1.750.905", texto.text.text)
        assertEquals(5, texto.offsetMapping.originalToTransformed(4))
        assertEquals(4, texto.offsetMapping.transformedToOriginal(5))
        assertEquals("1750905", original.text)
    }

    @Test fun horasSeMuestranConComaDecimal() {
        val texto = DecimalConComa.filter(AnnotatedString("2.5"))
        assertEquals("2,5", texto.text.text)
    }

    @Test fun salarioNoNumericoNoSeDisfrazaComoNumero() {
        val texto = SeparadorMiles.filter(AnnotatedString("abc"))
        assertEquals("abc", texto.text.text)
    }
}
