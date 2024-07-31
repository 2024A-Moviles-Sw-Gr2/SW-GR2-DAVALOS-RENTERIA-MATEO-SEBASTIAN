package com.example.deber_02.datos

import com.example.deber_02.modelos.BCarro

class BDatosMemoriaCarro {

    companion object {
        private val arregloCarros = arrayListOf<BCarro>()

        // Método para crear un nuevo carro
        fun crearCarro(carro: BCarro): Boolean {
            return arregloCarros.add(carro)
        }

        // Método para leer (obtener) todos los carros
        fun obtenerCarros(): List<BCarro> {
            return arregloCarros
        }

        // Método para obtener un carro por su ID
        fun obtenerCarroPorMarca(marca: String): BCarro? {
            return arregloCarros.find { it.marca == marca }
        }

        // Método para actualizar un carro por su ID
        fun actualizarCarro(marca: String, nuevoCarro: BCarro): Boolean {
            val indice = arregloCarros.indexOfFirst { it.marca == marca }
            return if (indice != -1) {
                arregloCarros[indice] = nuevoCarro
                true
            } else {
                false
            }
        }

        // Método para eliminar un carro por su ID
        fun eliminarCarro(marca: String): Boolean {
            val carro = obtenerCarroPorMarca(marca)
            return if (carro != null) {
                arregloCarros.remove(carro)
                true
            } else {
                false
            }
        }
    }
}
