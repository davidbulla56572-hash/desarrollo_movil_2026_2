# Guía de desarrollo: calculadora_nomina

Proyecto Android de un módulo (`:app`), Kotlin y Jetpack Compose. Al trabajar en este proyecto:

- Antes de cambiar código, inspecciona los archivos afectados, `app/build.gradle.kts` y los tests pertinentes. Mantén las convenciones existentes y limita los cambios al requerimiento.
- Aplica `android-beginner` como guía de nivel en todo desarrollo: prioriza conceptos de las clases 01–06 de la carpeta padre, código legible, cambios pequeños y explicaciones del porqué. Si otro skill propone algo más avanzado, prevalece esta guía salvo petición explícita o necesidad justificada.
- Organiza por responsabilidad: UI Compose, lógica de cálculo independiente de Android, recursos y pruebas. No agregues módulos, capas, frameworks ni dependencias sin una necesidad concreta. Si una funcionalidad crece, agrupa sus archivos por funcionalidad bajo `com.example.calculadora_nomina`.
- Además de `android-beginner`, usa `android-architecture` para nuevas funcionalidades o cambios de estructura; `compose-ui` para pantallas; `payroll-domain` para cálculos y reglas de nómina; `android-verification` para tests, dependencias o revisión final. Carga los skills pertinentes antes de implementar.
- Mantén los textos visibles en `res/values/strings.xml`, respeta tema/Material 3 y cubre reglas de negocio y casos límite con tests locales.
- No modifiques archivos generados o propios de la máquina (`.gradle/`, `build/`, `local.properties`, cachés de `.idea/`). No sobrescribas trabajo ajeno.
- Al terminar, ejecuta las comprobaciones aplicables mediante `gradlew.bat` en Windows (o `./gradlew` en Unix). Si no se pueden ejecutar, indica el motivo; no afirmes que pasaron.
