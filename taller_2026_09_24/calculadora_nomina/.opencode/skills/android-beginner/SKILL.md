---
name: Desarrollo Android nivel estudiante
description: Guía transversal para desarrollar este proyecto Android en Kotlin al nivel de las clases 01–06: cambios pequeños, Compose, estado, listas y navegación explicados paso a paso. Úsalo en cualquier implementación de la app.
---

# Objetivo

Construye funcionalidades que un estudiante pueda leer, ejecutar, explicar y ampliar. Este skill establece el nivel de complejidad; complementa los skills de arquitectura, UI, nómina y verificación, pero evita que conviertan un ejercicio inicial en una arquitectura de producción sin necesidad.

# Base curricular real

Las carpetas de las clases 01–06 están junto a `calculadora_nomina` en la carpeta padre. Antes de trabajar un tema, consulta su `Guia_Estudiante_ClaseXX.md` y, si procede, `Taller_ClaseXX.md`:

| Clase | Aprendizaje que se puede usar |
| --- | --- |
| 01 Ecosistema móvil y setup | Android Studio, emulador, estructura `app/src/main`, Gradle, `MainActivity`, `setContent`, recursos y manifiesto. |
| 02 Kotlin Fundamentals I | `val`/`var`, tipos, cadenas, conversiones seguras, nulabilidad, `if`, `when`, rangos y bucles. |
| 03 Kotlin Fundamentals II | funciones, `data class`, `sealed class` cuando aclare estados, lambdas, `filter`/`map`, funciones de extensión justificadas. |
| 04 UI Sistema de Vistas XML | La guía se enfoca en estructura Android, SDK, manifiesto, ciclo de vida, `Intent`, rotación y `Bundle`; XML/Views es contexto, no obligación de reescribir la UI Compose. |
| 05 UI Moderna Jetpack Compose I | `@Composable`, `@Preview`, `Column`/`Row`/`Box`, `Modifier`, Material 3, estado observable, `rememberSaveable` y estado elevado. |
| 06 Navegación y listas | `LazyColumn`, `items` con claves estables, filtrado, `NavHost`, rutas, argumentos, retroceso y evitar destinos duplicados. `RecyclerView` es referencia histórica. |

No asumas que se han enseñado ViewModel/MVVM, Room, Retrofit, Hilt, coroutines, Flow o Clean Architecture. Si el requerimiento necesita alguno, explícalo y pide acuerdo para introducirlo si hay una alternativa más sencilla.

# Cómo implementar cada petición

1. Ubica la funcionalidad en una o dos clases del cuadro y revisa los archivos existentes antes de editar. Di qué conceptos conocidos se van a practicar; si se sale del temario, adviértelo brevemente.
2. Propón la mínima solución funcional dentro de `:app`: para una sola pantalla, un Composable de pantalla y pequeñas funciones auxiliares; cuando crezca, separa `MainActivity.kt`, pantalla(s), modelos/cálculos y tests en archivos con nombres claros. No crees jerarquías de paquetes vacías.
3. Prefiere `val`, funciones pequeñas con nombres descriptivos y `data class` para datos relacionados; usa `?.`, `?:`, `toIntOrNull()`/`toLongOrNull()` o validación equivalente para entrada del usuario. Evita `!!` y errores ocultos. Usa `when` o `sealed class` solo cuando haga el comportamiento más legible.
4. En esta app, escribe la UI nueva en Compose, no mezcles Views/XML solo por la carpeta de clase 04. Define texto en `res/values/strings.xml`, reutiliza el tema y usa `@Preview`. Mantén estado simple en `remember` o `rememberSaveable` según si debe sobrevivir rotación; para componentes reutilizables, pasa valor y callback ("el estado baja, los eventos suben"). No mutar `mutableListOf` esperando recomposición.
5. Usa `LazyColumn` para colecciones desplazables, `items(..., key = { it.id })` cuando haya identidad estable; filtra sin perder el origen de los datos. Agrega Navigation Compose únicamente si realmente hay varias pantallas: pasa un ID, busca con `firstOrNull()` y maneja el ausente; conserva el retroceso y evita navegar repetidamente al mismo destino. No añadas dependencia solo para una vista.
6. Mantén cálculos de nómina en funciones Kotlin sin Compose/Android y verifica resultados con pruebas locales. Las clases usan `Double` para aprender tipos, pero los importes monetarios reales requieren una representación exacta (por ejemplo `Long` en unidades menores o decimal) y una regla de redondeo explicada; no inventes tasas ni normativas.
7. Explica cada cambio con: **qué archivo y por qué**, **cómo fluye el dato** (entrada → validación → cálculo → pantalla), **cómo probarlo**, y una idea de ejercicio pequeño para que el estudiante pueda modificarlo. Mantén las explicaciones proporcionales a la tarea; no sustituyas un entregable solicitado por teoría.
8. Verifica compilación/pruebas cuando el entorno lo permita y registra lo que no se pudo probar. No reemplaces verificación por la vista previa de Compose.

## Regla para crecer

Primero que funcione y se entienda; después extraer código repetido; por último introducir conceptos nuevos cuando resuelvan un problema concreto. Si el usuario pide explícitamente arquitectura profesional, se puede avanzar, pero mostrando el puente desde lo visto en clase.
