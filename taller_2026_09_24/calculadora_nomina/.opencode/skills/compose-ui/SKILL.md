---
name: Pantallas Jetpack Compose
description: Crea o modifica pantallas, formularios y estado UI en Jetpack Compose con accesibilidad, recursos y pruebas adecuadas.
---

# Flujo de interfaz

1. Revisa `MainActivity.kt`, `ui/theme/`, recursos y cualquier pantalla relacionada. Este proyecto usa Compose Material 3, no layouts XML para las pantallas.
2. Separa componentes de presentación de cálculos de negocio. Coloca estado en el propietario más cercano que lo comparta; expón valores y callbacks cuando convenga. Usa `rememberSaveable` para estado de entrada simple que deba sobrevivir recreaciones, y ViewModel si el estado o la lógica lo justifican.
3. Diseña campos, resultados y acciones con estados claros: vacío, error, en proceso si aplica y resultado. No ejecutes efectos secundarios ni cálculos costosos dentro de la composición; usa APIs de efectos o delega cuando corresponda.
4. Reutiliza `Calculadora_nominaTheme` y componentes Material 3. Guarda textos visibles y mensajes de error en `app/src/main/res/values/strings.xml`; usa `stringResource`, ofrece etiquetas comprensibles y semántica/accesibilidad en controles e iconos. Evita colores, tamaños o cadenas arbitrarias repetidas.
5. Adapta el diseño a tamaños y densidades diferentes; usa `Modifier`, espaciado coherente, desplazamiento cuando haya muchos campos, IME apropiado y el padding entregado por `Scaffold`.
6. Añade `@Preview` útil cuando sea viable y pruebas Compose en `androidTest` para interacciones críticas. Comprueba manualmente en emulador/dispositivo los flujos que no cubran tests.
7. Para reglas de cálculo, carga también `payroll-domain`; para cerrar el trabajo, sigue `android-verification`.
