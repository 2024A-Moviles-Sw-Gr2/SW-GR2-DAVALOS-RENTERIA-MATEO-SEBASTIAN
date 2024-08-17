package com.example.deber_02.mapa
class Ubicacion (
     val nombre: String,
     val latitud: Double,
     val longitud: Double
) {

    companion object {

        val listUbicaciones =  arrayListOf<Ubicacion>()

        fun getUbicaciones(): ArrayList<Ubicacion>{
            listUbicaciones.add(Ubicacion("Ubicacion1", -0.222716, -78.504362))
            listUbicaciones.add(Ubicacion("Ubicacion2", -0.190741, -78.484693))
            listUbicaciones.add(Ubicacion("Ubicacion3", -0.198432, -78.491332))


            return listUbicaciones
        }
    }
}