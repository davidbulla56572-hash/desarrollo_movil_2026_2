package com.example.calculadora_nomina

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class NominaCalculatorTest {
    private fun igualPesos(esperado: Double, actual: Double) {
        assertEquals(esperado, actual, 1.0)
    }

    @Test fun casoA_minimoConExtras() {
        val r = calcularNomina(1_750_905.0, 10.0, 4.0, false, false)
        igualPesos(8_338.0, r.valorHora)
        igualPesos(162_584.0, r.totalHorasExtra)
        igualPesos(249_095.0, r.auxilioTransporte)
        igualPesos(2_162_584.0, r.totalDevengado)
        igualPesos(76_540.0, r.aporteSalud)
        igualPesos(76_540.0, r.aportePension)
        igualPesos(0.0, r.fondoSolidaridad)
        igualPesos(2_009_505.0, r.salarioNeto)
        assertEquals(RangoSalarial.RANGO_1, clasificarRango(r.salarioBasico))
    }

    @Test fun casoB_dominical() {
        val r = calcularNomina(2_500_000.0, 6.0, 2.0, true, false)
        igualPesos(11_905.0, r.valorHora)
        igualPesos(216_667.0, r.totalHorasExtra)
        igualPesos(249_095.0, r.auxilioTransporte)
        igualPesos(2_965_762.0, r.totalDevengado)
        igualPesos(108_667.0, r.aporteSalud)
        igualPesos(108_667.0, r.aportePension)
        igualPesos(2_748_428.0, r.salarioNeto)
        assertEquals(RangoSalarial.RANGO_1, clasificarRango(r.salarioBasico))
    }

    @Test fun casoC_transporteEmpresa() {
        val r = calcularNomina(3_000_000.0, 5.0, 0.0, false, true)
        igualPesos(14_286.0, r.valorHora)
        igualPesos(89_286.0, r.totalHorasExtra)
        igualPesos(0.0, r.auxilioTransporte)
        igualPesos(3_089_286.0, r.totalDevengado)
        igualPesos(123_571.0, r.aporteSalud)
        igualPesos(123_571.0, r.aportePension)
        igualPesos(2_842_143.0, r.salarioNeto)
    }

    @Test fun casoD_fondoSolidaridad() {
        val r = calcularNomina(8_000_000.0, 0.0, 0.0, false, false)
        igualPesos(38_095.0, r.valorHora)
        igualPesos(0.0, r.totalHorasExtra)
        igualPesos(0.0, r.auxilioTransporte)
        igualPesos(320_000.0, r.aporteSalud)
        igualPesos(320_000.0, r.aportePension)
        igualPesos(80_000.0, r.fondoSolidaridad)
        igualPesos(7_280_000.0, r.salarioNeto)
        assertEquals(RangoSalarial.RANGO_3, clasificarRango(r.salarioBasico))
    }

    @Test fun limitesDeAuxilioYFondo() {
        val conAuxilio = calcularNomina(2 * SALARIO_MINIMO, 0.0, 0.0, false, false)
        igualPesos(AUXILIO_TRANSPORTE, conAuxilio.auxilioTransporte)
        assertEquals(RangoSalarial.RANGO_1, clasificarRango(conAuxilio.salarioBasico))

        val sinAuxilio = calcularNomina(2 * SALARIO_MINIMO + 1, 0.0, 0.0, false, false)
        igualPesos(0.0, sinAuxilio.auxilioTransporte)
        assertEquals(RangoSalarial.RANGO_2, clasificarRango(sinAuxilio.salarioBasico))

        val justoDebajo = calcularNomina(4 * SALARIO_MINIMO - 1, 0.0, 0.0, false, false)
        igualPesos(0.0, justoDebajo.fondoSolidaridad)
        val justoEn = calcularNomina(4 * SALARIO_MINIMO, 0.0, 0.0, false, false)
        igualPesos(justoEn.salarioBasico * 0.01, justoEn.fondoSolidaridad)
        assertEquals(RangoSalarial.RANGO_3, clasificarRango(justoEn.salarioBasico))
    }

    @Test fun fondoDependeDelIbcNoSoloDelSalarioBasico() {
        val r = calcularNomina(6_900_000.0, 4.0, 0.0, false, false)
        igualPesos(r.totalDevengado * 0.01, r.fondoSolidaridad)
        assertEquals(RangoSalarial.RANGO_2, clasificarRango(r.salarioBasico))
    }

    @Test fun horasEnElLimiteYValoresInvalidos() {
        calcularNomina(SALARIO_MINIMO, 24.0, 24.0, false, false)
        assertThrows(IllegalArgumentException::class.java) { calcularNomina(1_000_000.0, 0.0, 0.0, false, false) }
        assertThrows(IllegalArgumentException::class.java) { calcularNomina(2_000_000.0, -1.0, 0.0, false, false) }
        assertThrows(IllegalArgumentException::class.java) { calcularNomina(2_000_000.0, 30.0, 20.0, false, false) }
        assertThrows(IllegalArgumentException::class.java) { calcularNomina(Double.POSITIVE_INFINITY, 0.0, 0.0, false, false) }
        assertThrows(IllegalArgumentException::class.java) { calcularNomina(Double.MAX_VALUE, 1.0, 0.0, false, false) }
    }
}
