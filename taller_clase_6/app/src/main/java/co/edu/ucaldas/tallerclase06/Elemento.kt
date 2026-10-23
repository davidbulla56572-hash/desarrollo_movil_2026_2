package co.edu.ucaldas.tallerclase06

data class Elemento(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val descripcionCorta: String,
    val descripcionLarga: String
)

val elementos = listOf(
    Elemento(1, "Variables", "Kotlin", "Guardan datos que pueden cambiar.", "En Kotlin se usa val para un valor que no se reasigna y var para uno que sí puede cambiar. Por ejemplo, val nombre = \"Ana\" y var edad = 20."),
    Elemento(2, "Condicionales", "Kotlin", "Permiten tomar decisiones.", "Con if y else podemos ejecutar código según una condición. Kotlin también tiene when para comparar varios casos de forma clara."),
    Elemento(3, "Funciones", "Kotlin", "Agrupan instrucciones reutilizables.", "Una función se declara con fun. Puede recibir parámetros y devolver un resultado. Por ejemplo, fun sumar(a: Int, b: Int): Int = a + b."),
    Elemento(4, "Clases de datos", "Kotlin", "Representan información de manera simple.", "Una data class permite agrupar propiedades de un objeto. Kotlin genera funciones útiles como toString, equals y copy automáticamente."),
    Elemento(5, "Column y Row", "Compose", "Organizan elementos en vertical u horizontal.", "Column coloca los componentes uno debajo de otro. Row los pone uno al lado del otro. Ambos permiten ajustar espacios y alineación."),
    Elemento(6, "Text y Button", "Compose", "Muestran texto y reciben pulsaciones.", "Text muestra contenido escrito en pantalla. Button crea un botón y recibe una función onClick que se ejecuta al tocarlo."),
    Elemento(7, "Estado con remember", "Compose", "Conserva valores entre recomposiciones.", "remember guarda un valor mientras la pantalla está en composición. Si se combina con mutableStateOf, Compose actualiza la interfaz cuando cambia el valor."),
    Elemento(8, "Listas con LazyColumn", "Compose", "Muestran listas desplazables.", "LazyColumn presenta elementos en una lista vertical y compone los que hacen falta en pantalla. Con items se dibuja cada elemento de la lista."),
    Elemento(9, "NavController", "Navegación", "Controla los cambios entre pantallas.", "NavController permite abrir un destino con navigate y regresar al anterior con popBackStack. También mantiene la pila de navegación."),
    Elemento(10, "NavHost", "Navegación", "Define las rutas de la aplicación.", "NavHost relaciona las rutas con sus pantallas Composable. Tiene una ruta inicial, que en esta aplicación es la lista de temas."),
    Elemento(11, "Argumentos de ruta", "Navegación", "Envían datos a otra pantalla.", "En la ruta detalle/{elementoId} se recibe un número entero. Así se puede buscar el tema por su ID sin enviar el objeto completo."),
    Elemento(12, "Pila de navegación", "Navegación", "Guarda el historial de pantallas.", "Al navegar se agrega un destino a la pila. Al volver, popBackStack retira el destino actual y muestra el anterior. launchSingleTop evita duplicar el mismo destino en el tope.")
)
