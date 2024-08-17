package com.example.deber_02.datos

import com.example.deber_02.modelos.BCaracteristica

class BDatosMemoriaCaracteristica {

    companion object {
        private val arregloCaracteristicas = arrayListOf<BCaracteristica>(

        )
        public var idsCaracteristica = 0

        // Método para crear una nueva característica
        fun crearCaracteristica(caracteristica: BCaracteristica): Boolean {
            return arregloCaracteristicas.add(caracteristica)
        }

        // Método para leer (obtener) todas las características
        fun obtenerCaracteristicas(): List<BCaracteristica> {
            return arregloCaracteristicas
        }

        // Método para obtener una característica por su ID
        fun obtenerCaracteristicaPorId(id: Int): BCaracteristica? {
            return arregloCaracteristicas.find { it.id == id }
        }

        // Método para actualizar una característica por su ID
        fun actualizarCaracteristica(id: Int, nuevaCaracteristica: BCaracteristica): Boolean {
            val indice = arregloCaracteristicas.indexOfFirst { it.id == id }
            return if (indice != -1) {
                arregloCaracteristicas[indice] = nuevaCaracteristica
                true
            } else {
                false
            }
        }

        // Método para eliminar una característica por su ID
        fun eliminarCaracteristica(id: Int): Boolean {
            val caracteristica = obtenerCaracteristicaPorId(id)
            return if (caracteristica != null) {
                arregloCaracteristicas.remove(caracteristica)
                true
            } else {
                false
            }
        }
    }
}