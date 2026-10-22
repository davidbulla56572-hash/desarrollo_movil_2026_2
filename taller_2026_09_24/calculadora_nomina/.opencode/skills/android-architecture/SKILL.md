---
name: Arquitectura Android escalable
description: Diseña e implementa funcionalidades Kotlin Android con una estructura incremental, paquetes coherentes y responsabilidades separadas. Úsalo al añadir funciones o reorganizar código.
---

# Flujo de arquitectura

1. Lee `AGENTS.md`, `settings.gradle.kts`, `app/build.gradle.kts` y los archivos relacionados con el cambio. Confirma que este proyecto usa un módulo `:app` y Compose antes de proponer una estructura.
2. Identifica entradas, salidas, estado y reglas de negocio. Decide el cambio más pequeño que mantenga la separación de responsabilidades.
3. Mantén `MainActivity` como entrada Android y composición de la pantalla, no como almacén de reglas de nómina. Agrupa archivos por funcionalidad cuando aparezcan varias piezas relacionadas: por ejemplo `payroll/ui/` y `payroll/domain/`; conserva `ui/theme/` para el tema compartido. Evita un archivo gigante y evita carpetas vacías.
4. Aísla cálculos y modelos puros del SDK Android. La UI muestra estado y envía eventos; si la pantalla acumula estado o lógica asíncrona, considera un ViewModel con la dependencia correspondiente. Introduce repositorios, persistencia, inyección de dependencias o módulos Gradle adicionales solo si existe una necesidad real, y explica el costo.
5. Usa nombres Kotlin descriptivos y consistentes (`PascalCase` para tipos, `camelCase` para funciones/propiedades, paquetes en minúsculas); sigue los paquetes y estilo del código existente. Mantén dependencias en `gradle/libs.versions.toml` y su uso en `app/build.gradle.kts`, sin versiones duplicadas dispersas.
6. Si mueves clases, actualiza paquetes, imports, referencias del manifiesto y tests. No cambies `applicationId` ni `namespace` por una simple reorganización de paquetes.
7. Completa con tests del comportamiento y las verificaciones de `android-verification`. Resume las decisiones estructurales y cualquier dependencia nueva.

Regla de escala: primero una solución clara en `:app`; extraer abstracciones solo cuando simplifiquen una necesidad observada, no por anticipación.
