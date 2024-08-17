package com.example.deber_02.datos

import com.example.deber_02.modelos.BPlaneta
import java.util.ArrayList
import java.util.Date

class BDatosMemoriaPlaneta {

    companion object {
        private val arregloPlanetas = arrayListOf<BPlaneta>()
        public var idsPlaneta = 0

        // Método para crear un nuevo planeta
        fun crearPlaneta(planeta: BPlaneta): Boolean {
            return arregloPlanetas.add(planeta)
        }

        // Método para leer (obtener) todos los planetas
        fun obtenerPlanetas(): List<BPlaneta> {
            return arregloPlanetas
        }

        // Método para obtener un planeta por su ID
        fun obtenerPlanetaPorId(id: Int): BPlaneta? {
            return arregloPlanetas.find { it.id == id }
        }

        // Método para actualizar un planeta por su ID
        fun actualizarPlaneta(id: Int, nuevoPlaneta: BPlaneta): Boolean {
            val indice = arregloPlanetas.indexOfFirst { it.id == id }
            return if (indice != -1) {
                arregloPlanetas[indice] = nuevoPlaneta
                true
            } else {
                false
            }
        }

        // Método para eliminar un planeta por su ID
        fun eliminarPlaneta(id: Int): Boolean {
            val planeta = obtenerPlanetaPorId(id)
            return if (planeta != null) {
                arregloPlanetas.remove(planeta)
                true
            } else {
                false
            }
        }
    }
}
