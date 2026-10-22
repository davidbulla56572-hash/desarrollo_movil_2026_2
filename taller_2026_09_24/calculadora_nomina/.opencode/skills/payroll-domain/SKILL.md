---
name: Dominio de nómina
description: Implementa cálculos de salario, deducciones y resultados de nómina en Kotlin puro con precisión, reglas explícitas y pruebas de límites.
---

# Flujo de cálculo

1. Confirma con el requerimiento la moneda, jurisdicción, período, tasas, redondeo y significado de cada concepto. Si faltan reglas indispensables, pregunta o presenta supuestos explícitos; no inventes obligaciones fiscales o laborales.
2. Define entradas y salidas con tipos claros. Separa parseo/validación de formularios de las operaciones de dominio y de la presentación del resultado. No acoples las funciones de cálculo a `Activity`, `Context` ni Compose.
3. Evita `Float`/`Double` para importes monetarios; usa una representación decimal o unidades menores enteras según el caso, con política de redondeo explícita. No escondas tasas ni constantes legales en expresiones: asígnales nombres y señala de dónde provienen cuando proceda.
4. Define comportamiento para cero, negativos, valores faltantes, tasas fuera de rango, importes grandes y redondeos. Haz que las entradas inválidas produzcan errores claros en lugar de resultados silenciosamente incorrectos.
5. Escribe pruebas en `app/src/test/java/com/example/calculadora_nomina/` para cada regla y caso límite. Usa ejemplos con resultados esperados calculados de forma independiente; no repitas la implementación en el test.
6. Conecta el dominio con la UI mediante valores/resultados explícitos. Si las reglas cambian por fecha o jurisdicción, modela esa variación antes de ramificar código por toda la pantalla.
7. Sigue `android-verification` para ejecutar las pruebas y reportar los límites de la validación.
