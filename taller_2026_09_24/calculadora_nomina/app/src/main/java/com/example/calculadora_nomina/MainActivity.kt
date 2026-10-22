package com.example.calculadora_nomina

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora_nomina.ui.theme.Calculadora_nominaTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculadora_nominaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaNomina(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaNomina(modifier: Modifier = Modifier) {
    var salario by remember { mutableStateOf("") }
    var horasDiurnas by remember { mutableStateOf("") }
    var horasNocturnas by remember { mutableStateOf("") }
    var esDominical by remember { mutableStateOf(false) }
    var transporteEmpresa by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<ResultadoNomina?>(null) }
    var error by remember { mutableStateOf<Int?>(null) }
    var errorSalario by remember { mutableStateOf(false) }
    var errorDiurnas by remember { mutableStateOf(false) }
    var errorNocturnas by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun calcular(dominical: Boolean = esDominical, transporte: Boolean = transporteEmpresa) {
        error = null
        errorSalario = false
        errorDiurnas = false
        errorNocturnas = false

        val textoSalario = salario.trim()
        val sueldo = if (textoSalario.matches(Regex("[0-9]+")) ||
            textoSalario.matches(Regex("[0-9]{1,3}(\\.[0-9]{3})+"))) {
            textoSalario.replace(".", "").toDoubleOrNull()
        } else null
        if (sueldo == null || !sueldo.isFinite()) {
            error = R.string.error_salario_invalido
            errorSalario = true
            resultado = null
            return
        }
        if (sueldo < SALARIO_MINIMO) {
            error = R.string.error_salario_minimo
            errorSalario = true
            resultado = null
            return
        }

        val diurnas = if (horasDiurnas.isBlank()) 0.0 else horasDiurnas.trim().replace(',', '.').toDoubleOrNull()
        val nocturnas = if (horasNocturnas.isBlank()) 0.0 else horasNocturnas.trim().replace(',', '.').toDoubleOrNull()
        if (diurnas == null || !diurnas.isFinite() || diurnas < 0) {
            error = R.string.error_horas
            errorDiurnas = true
            resultado = null
            return
        }
        if (nocturnas == null || !nocturnas.isFinite() || nocturnas < 0) {
            error = R.string.error_horas
            errorNocturnas = true
            resultado = null
            return
        }
        if (diurnas + nocturnas > 48) {
            error = R.string.error_limite_horas
            errorDiurnas = true
            errorNocturnas = true
            resultado = null
            return
        }

        // El cálculo está en NominaCalculator.kt; aquí solo validamos la entrada del formulario.
        resultado = try {
            calcularNomina(sueldo, diurnas, nocturnas, dominical, transporte)
        } catch (_: IllegalArgumentException) {
            error = R.string.error_salario_invalido
            errorSalario = true
            null
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.titulo), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.subtitulo), style = MaterialTheme.typography.bodyLarge)
            }
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(stringResource(R.string.tus_datos), style = MaterialTheme.typography.titleLarge)
                CampoNumerico(
                    etiqueta = R.string.salario_basico,
                    valor = salario,
                    onValueChange = { salario = it; resultado = null; error = null; errorSalario = false },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    isError = errorSalario,
                    esSalario = true,
                    ayuda = R.string.ayuda_salario,
                    modifier = Modifier.fillMaxWidth()
                )
                CampoNumerico(
                    etiqueta = R.string.horas_diurnas,
                    valor = horasDiurnas,
                    onValueChange = { horasDiurnas = it; resultado = null; error = null; errorDiurnas = false },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    isError = errorDiurnas,
                    ayuda = R.string.ayuda_horas,
                    modifier = Modifier.fillMaxWidth()
                )
                CampoNumerico(
                    etiqueta = R.string.horas_nocturnas,
                    valor = horasNocturnas,
                    onValueChange = { horasNocturnas = it; resultado = null; error = null; errorNocturnas = false },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                    isError = errorNocturnas,
                    ayuda = R.string.ayuda_horas,
                    modifier = Modifier.fillMaxWidth(),
                    onDone = { focusManager.clearFocus(); keyboardController?.hide() }
                )
            }
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(R.string.condiciones), style = MaterialTheme.typography.titleLarge)
                FilaInterruptor(
                    etiqueta = R.string.domingo_festivo,
                    valor = esDominical,
                    onCheckedChange = {
                        esDominical = it
                        if (resultado != null) calcular(dominical = it)
                    }
                )
                FilaInterruptor(
                    etiqueta = R.string.transporte_empresa,
                    valor = transporteEmpresa,
                    onCheckedChange = {
                        transporteEmpresa = it
                        if (resultado != null) calcular(transporte = it)
                    }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { focusManager.clearFocus(); keyboardController?.hide(); calcular() }, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.calcular))
            }
            OutlinedButton(onClick = {
                salario = ""
                horasDiurnas = ""
                horasNocturnas = ""
                esDominical = false
                transporteEmpresa = false
                resultado = null
                error = null
                errorSalario = false
                errorDiurnas = false
                errorNocturnas = false
                focusManager.clearFocus()
                keyboardController?.hide()
            }) {
                Text(stringResource(R.string.limpiar))
            }
        }

        error?.let { mensaje ->
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                Text(stringResource(mensaje), color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(16.dp))
            }
        }

        resultado?.let { nomina ->
            val rango = clasificarRango(nomina.salarioBasico)
            val imagen = when (rango) {
                RangoSalarial.RANGO_1 -> R.drawable.rango_1
                RangoSalarial.RANGO_2 -> R.drawable.rango_2
                RangoSalarial.RANGO_3 -> R.drawable.rango_3
            }
            val textoRango = when (rango) {
                RangoSalarial.RANGO_1 -> R.string.rango_1
                RangoSalarial.RANGO_2 -> R.string.rango_2
                RangoSalarial.RANGO_3 -> R.string.rango_3
            }
            val descripcion = when (rango) {
                RangoSalarial.RANGO_1 -> R.string.descripcion_rango_1
                RangoSalarial.RANGO_2 -> R.string.descripcion_rango_2
                RangoSalarial.RANGO_3 -> R.string.descripcion_rango_3
            }

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Image(painterResource(imagen), contentDescription = stringResource(descripcion))
                        Text(stringResource(textoRango), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                    }
                    Text(stringResource(R.string.devengado), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    LineaResultado(R.string.valor_hora, nomina.valorHora)
                    LineaResultado(R.string.salario_basico, nomina.salarioBasico)
                    LineaResultado(R.string.total_horas_extra, nomina.totalHorasExtra)
                    LineaResultado(R.string.auxilio_transporte, nomina.auxilioTransporte)
                    LineaResultado(R.string.total_devengado, nomina.totalDevengado, destacado = true)
                    Text(stringResource(R.string.deducciones), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    LineaResultado(R.string.salud, nomina.aporteSalud)
                    LineaResultado(R.string.pension, nomina.aportePension)
                    LineaResultado(R.string.fondo, nomina.fondoSolidaridad)
                    LineaResultado(R.string.total_deducciones, nomina.totalDeducciones, destacado = true)
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.neto), style = MaterialTheme.typography.titleMedium)
                    Text(formatearPesos(nomina.salarioNeto), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                }
            }
        } ?: run {
            if (error == null) {
                Text(stringResource(R.string.instruccion_inicial), style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text(stringResource(R.string.aviso_academico), style = MaterialTheme.typography.bodySmall)
        androidx.compose.foundation.layout.Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun CampoNumerico(
    @StringRes etiqueta: Int,
    valor: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    esSalario: Boolean = false,
    @StringRes ayuda: Int,
    onDone: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var tieneFoco by remember { mutableStateOf(false) }
    LaunchedEffect(tieneFoco) {
        if (tieneFoco) keyboardController?.show()
    }
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(stringResource(etiqueta)) },
        prefix = if (esSalario) {{ Text(stringResource(R.string.simbolo_peso)) }} else null,
        supportingText = { Text(stringResource(ayuda)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Next) },
            onDone = { onDone() }
        ),
        isError = isError,
        visualTransformation = if (esSalario) SeparadorMiles else DecimalConComa,
        singleLine = true,
        modifier = modifier.onFocusChanged { tieneFoco = it.isFocused }
    )
}

// El usuario escribe dígitos normales; solo cambia cómo se ven en el campo.
// Así el cálculo recibe 1750905 aunque en pantalla se lea 1.750.905.
internal object SeparadorMiles : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        if (original.any { it !in '0'..'9' }) return TransformedText(text, OffsetMapping.Identity)
        val visible = StringBuilder()
        val posiciones = IntArray(original.length + 1)
        for (i in original.indices) {
            if (i > 0 && (original.length - i) % 3 == 0) visible.append('.')
            visible.append(original[i])
            posiciones[i + 1] = visible.length
        }
        val mapa = object : OffsetMapping {
            override fun originalToTransformed(offset: Int) = posiciones[offset.coerceIn(0, original.length)]
            override fun transformedToOriginal(offset: Int) = visible.take(offset.coerceIn(0, visible.length)).count { it != '.' }
        }
        return TransformedText(AnnotatedString(visible.toString()), mapa)
    }
}

internal object DecimalConComa : VisualTransformation {
    override fun filter(text: AnnotatedString) = TransformedText(
        AnnotatedString(text.text.replace('.', ',')), OffsetMapping.Identity
    )
}

@Composable
fun FilaInterruptor(@StringRes etiqueta: Int, valor: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(etiqueta), modifier = Modifier.weight(1f))
        Switch(checked = valor, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun LineaResultado(@StringRes etiqueta: Int, valor: Double, destacado: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            stringResource(etiqueta),
            modifier = Modifier.weight(1f),
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal,
            style = if (etiqueta == R.string.neto) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
        )
        Text(
            formatearPesos(valor),
            fontWeight = if (destacado) FontWeight.Bold else FontWeight.Normal,
            style = if (etiqueta == R.string.neto) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
        )
    }
}

fun formatearPesos(valor: Double): String {
    val formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"))
    formato.minimumFractionDigits = 0
    formato.maximumFractionDigits = 0
    return formato.format(valor)
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PantallaNominaPreview() {
    Calculadora_nominaTheme { PantallaNomina() }
}
