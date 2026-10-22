---
name: Verificación Android Kotlin
description: Revisa y valida cambios Android con tests, compilación, revisión de recursos y dependencias antes de dar una tarea por terminada.
---

# Lista de cierre

1. Inspecciona el cambio: verifica paquetes/imports, referencias a recursos, manifest, compatibilidad con `minSdk` y ausencia de lógica de negocio en la UI. Comprueba que no se tocaron archivos generados, cachés ni configuración local.
2. Ubica pruebas puras en `app/src/test/` y pruebas que requieren Android o Compose en `app/src/androidTest/`. Añade casos felices, inválidos y límites pertinentes; no uses solamente los tests de ejemplo que vienen de la plantilla.
3. Si cambian dependencias, actualiza el catálogo `gradle/libs.versions.toml` y `app/build.gradle.kts`, y justifica por qué se necesitan. Evita introducir una biblioteca para una tarea trivial.
4. Desde la raíz `calculadora_nomina`, en Windows ejecuta `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug` (en Unix, `./gradlew ...`). Si hay emulador/dispositivo disponible y los cambios lo requieren, ejecuta también `:app:connectedDebugAndroidTest`.
5. Si Gradle, el SDK o el emulador no están disponibles, registra exactamente qué no pudiste comprobar; no confundas revisión estática con ejecución real.
6. Entrega un resumen de cambios, pruebas ejecutadas con resultado y riesgos pendientes. No declares que la app funciona en dispositivo sin haberlo comprobado.
