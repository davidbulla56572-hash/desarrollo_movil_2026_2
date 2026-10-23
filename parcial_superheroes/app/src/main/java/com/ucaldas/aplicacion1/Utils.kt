package com.ucaldas.aplicacion1

object Utils {
    object Rutas {
        const val LISTA = "lista"
        const val DETALLE = "detalle/{id}"

        fun detalle(id: Int) = "detalle/$id"
    }
}
