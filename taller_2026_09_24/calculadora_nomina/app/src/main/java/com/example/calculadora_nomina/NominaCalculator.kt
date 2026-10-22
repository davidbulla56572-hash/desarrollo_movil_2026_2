package com.example.calculadora_nomina

// Decreto 1469 de 2025: salario mínimo de 2026 (valor indicado en el taller).
const val SALARIO_MINIMO = 1_750_905.0
// Decreto 1470 de 2025: auxilio de transporte de 2026 (valor indicado en el taller).
const val AUXILIO_TRANSPORTE = 249_095.0
// Ley 2101 de 2021: jornada de 42 horas semanales desde julio de 2026; 210 horas mensuales en el taller.
const val HORAS_MENSUALES = 210.0
// Ley 100 de 1993: aportes del trabajador a salud y pensión usados en el taller.
const val TASA_SALUD = 0.04
const val TASA_PENSION = 0.04
// Ley 797 de 2003: aporte al Fondo de Solidaridad Pensional usado en el taller.
const val TASA_FONDO = 0.01

data class ResultadoNomina(
    val valorHora: Double,
    val salarioBasico: Double,
    val totalHorasExtra: Double,
    val auxilioTransporte: Double,
    val totalDevengado: Double,
    val aporteSalud: Double,
    val aportePension: Double,
    val fondoSolidaridad: Double,
    val totalDeducciones: Double,
    val salarioNeto: Double
)

enum class RangoSalarial { RANGO_1, RANGO_2, RANGO_3 }

fun clasificarRango(salarioBasico: Double): RangoSalarial = when {
    salarioBasico <= 2 * SALARIO_MINIMO -> RangoSalarial.RANGO_1
    salarioBasico < 4 * SALARIO_MINIMO -> RangoSalarial.RANGO_2
    else -> RangoSalarial.RANGO_3
}

fun calcularNomina(
    salarioBasico: Double,
    horasDiurnas: Double,
    horasNocturnas: Double,
    esDominical: Boolean,
    transporteEmpresa: Boolean
): ResultadoNomina {
    require(salarioBasico.isFinite() && salarioBasico >= SALARIO_MINIMO)
    require(horasDiurnas.isFinite() && horasNocturnas.isFinite())
    require(horasDiurnas >= 0 && horasNocturnas >= 0 && horasDiurnas + horasNocturnas <= 48)

    val valorHora = salarioBasico / HORAS_MENSUALES
    val factorDiurno = if (esDominical) 2.15 else 1.25
    val factorNocturno = if (esDominical) 2.65 else 1.75
    val pagoExtrasDiurnas = horasDiurnas * valorHora * factorDiurno
    val pagoExtrasNocturnas = horasNocturnas * valorHora * factorNocturno
    val totalHorasExtra = pagoExtrasDiurnas + pagoExtrasNocturnas
    val ibc = salarioBasico + totalHorasExtra
    val auxilio = if (salarioBasico <= 2 * SALARIO_MINIMO && !transporteEmpresa) AUXILIO_TRANSPORTE else 0.0
    val totalDevengado = ibc + auxilio
    val salud = ibc * TASA_SALUD
    val pension = ibc * TASA_PENSION
    val fondo = if (ibc >= 4 * SALARIO_MINIMO) ibc * TASA_FONDO else 0.0
    val deducciones = salud + pension + fondo
    val neto = totalDevengado - deducciones
    require(neto.isFinite()) { "El importe excede el rango numérico admitido" }

    return ResultadoNomina(valorHora, salarioBasico, totalHorasExtra, auxilio, totalDevengado,
        salud, pension, fondo, deducciones, neto)
}
