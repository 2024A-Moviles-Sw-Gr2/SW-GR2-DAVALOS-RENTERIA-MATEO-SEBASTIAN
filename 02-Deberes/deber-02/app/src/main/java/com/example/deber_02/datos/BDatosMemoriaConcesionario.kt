package com.example.deber_02.datos

import com.example.deber_02.modelos.BConcesionario

class BDatosMemoriaConcesionario {

    companion object {
        private val arregloConcesionarios = arrayListOf<BConcesionario>()
        public var idsConcesionario = 0

        // Método para crear un nuevo concesionario
        fun crearConcesionario(concesionario: BConcesionario): Boolean {
            return arregloConcesionarios.add(concesionario)
        }

        // Método para leer (obtener) todos los concesionarios
        fun obtenerConcesionarios(): List<BConcesionario> {
            return arregloConcesionarios
        }

        // Método para obtener un concesionario por su ID
        fun obtenerConcesionario(nombre: String): BConcesionario? {
            return arregloConcesionarios.find { it.nombre == nombre }
        }

        // Método para actualizar un concesionario por su ID
        fun actualizarConcesionario(nombre: String, nuevoConcesionario: BConcesionario): Boolean {
            val indice = arregloConcesionarios.indexOfFirst { it.nombre == nombre }
            return if (indice != -1) {
                arregloConcesionarios[indice] = nuevoConcesionario
                true
            } else {
                false
            }
        }

        // Método para eliminar un concesionario por su ID
        fun eliminarConcesionario(nombre: String): Boolean {
            val concesionario = obtenerConcesionario(nombre)
            return if (concesionario != null) {
                arregloConcesionarios.remove(concesionario)
                true
            } else {
                false
            }
        }
    }
}
