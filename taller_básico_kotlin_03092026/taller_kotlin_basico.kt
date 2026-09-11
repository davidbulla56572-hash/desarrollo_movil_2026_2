fun ejercicio1_Temperatura() { 
 // TODO: Declara la variable celsius
 val celsius = 25.5
 
 // TODO: Calcula fahrenheit usando la fórmula    
 val fahrenheit = (celsius * (9.0/5.0)) + 32
 
 // TODO: Imprime el resultado 
 println("${celsius} °C equivale a ${fahrenheit} °F")
} 


fun ejercicio2_IMC() { 
 // TODO: Declara peso y altura 
 	val peso = 70.0
	val altura = 1.75
 // TODO: Calcula el IMC 
 	val imc = peso / (altura * altura)
 // TODO: Usa when para clasificar 
 val clasificacion = when{
        imc < 18.5 -> "Bajo peso"
        imc in 18.5..24.9 -> "Peso normal"
        imc in 25.0..29.9 -> "Sobrepeso"
        else -> "Obesidad"
    }
 // TODO: Imprime los resultados 
 println("Peso: ${peso}kg, Altura: ${altura}m") 
 println("IMC: %.2f".format(imc)) 
 println("Clasificación: $clasificacion") 
}

fun ejercicio3_ParImpar() { 
 // TODO: Declara la variable numero 
 	val num = 9
 // TODO: Usa if como expresión 
 	val resultado = if (num % 2 == 0) "Par" else "Impar" 
 // TODO: Imprime el resultado
	println(resultado)
} 


fun ejercicio4_NumeroPrimo() { 
 val numero = 17 
 var esPrimo = true 
 // TODO: Verifica si el número es menor o igual a 1 
 if(numero <= 1){
     esPrimo = false
 }
 
 // TODO: Usa un for loop para buscar divisores 
 for (i in 2 until numero) { 
     // Si encuentra un divisor, no es primo
     if(numero % i == 0){
         esPrimo = false
         break
     }
 } 
 // TODO: Imprime el resultado 
 	val message = if(esPrimo) "El número $numero ES primo" else "El número $numero NO es primo" 
	println(message)
} 

fun ejercicio5_SumaPares() { 
 var suma = 0 
 // TODO: Usa un for loop del 1 al 100 
 for (i in 1..100) { 
 // TODO: Verifica si i es par y súmalo 
     if(i % 2 == 0){
         suma += i
     }
 } 
 // TODO: Imprime el resultado 
 println("El total es $suma")
} 

fun ejercicio6_Factorial() { 
 val numero = 5 
 var factorial: Long = 1 
 // TODO: Usa un for loop de 1 hasta numero 
	for(i in 1..numero){
        factorial = factorial * i
    }
 // TODO: Imprime el resultado 
 println("El factorial de $numero es $factorial")
} 

fun ejercicio7_Palindromo() { 
 val palabra = "anilina"
 // TODO: Verifica si es palíndromo 
 val esPalindromo = palabra.reversed() == palabra
 // TODO: Usa if para crear el mensaje 
 val resultado = if (esPalindromo) "ES un palíndromo" else "NO es un  palíndromo" 
 // TODO: Imprime el resultado 
 println("La palabra $palabra $resultado")
} 

fun main() { 
 println("=" .repeat(50)) 
 println("TALLER 1: KOTLIN FUNDAMENTALS") 
 println("Nombre: [Tu nombre aquí]") 
 println("=" .repeat(50)) 
 println() 
 println("--- Ejercicio 1: Conversor de Temperatura ---") 
 ejercicio1_Temperatura() 
 println() 
 println("--- Ejercicio 2: Calculadora de IMC ---") 
 ejercicio2_IMC() 
 println() 
 println("--- Ejercicio 3: Número Par o Impar ---") 
 ejercicio3_ParImpar() 
 println() 
 println("--- Ejercicio 4: Números Primos ---") 
 ejercicio4_NumeroPrimo() 
 println() 
 println("--- Ejercicio 5: Suma de Números Pares ---") 
 ejercicio5_SumaPares() 
 println() 
 println("--- Ejercicio 6: Factorial ---") 
 ejercicio6_Factorial() 
 println() 
 println("--- Ejercicio 7: Palíndromo ---") 
 ejercicio7_Palindromo() 
 println() 
 println("=" .repeat(50)) 
 println("FIN DEL TALLER") 
 println("=" .repeat(50)) 
} 



