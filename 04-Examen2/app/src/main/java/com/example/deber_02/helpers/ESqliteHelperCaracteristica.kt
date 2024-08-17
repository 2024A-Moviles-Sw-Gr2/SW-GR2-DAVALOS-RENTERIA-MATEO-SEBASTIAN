package com.example.deber_02.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber_02.modelos.BCaracteristica

class ESqliteHelperCaracteristica(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "caracteristicas",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCaracteristica = """
            CREATE TABLE CARACTERISTICA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo VARCHAR(50),
                descripcion VARCHAR(50),
                planetaId INTEGER 
            )
            
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCaracteristica)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario
    }

    fun crearCaracteristica(
        titulo: String,
        descripcion: String,
        planetaId: Int
    ): Boolean {
        val db = writableDatabase
        val valoresCaracteristica = ContentValues()
        valoresCaracteristica.put("titulo", titulo)
        valoresCaracteristica.put("descripcion", descripcion)
        valoresCaracteristica.put("planetaId", planetaId)

        val resultado = db.insert("CARACTERISTICA", null, valoresCaracteristica)
        db.close()
        return resultado != -1L
    }

    fun eliminarCaracteristica(id: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(id.toString())
        val resultadoEliminar = db.delete("CARACTERISTICA", "id=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    fun actualizarCaracteristica(
        id: Int,
        titulo: String,
        descripcion: String
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("titulo", titulo)
        valores.put("descripcion", descripcion)

        val parametros = arrayOf(id.toString())
        val resultado = db.update("CARACTERISTICA", valores, "id=?", parametros)
        db.close()
        return resultado != -1
    }

    fun consultarCaracteristicaPorID(id: Int): BCaracteristica? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM CARACTERISTICA WHERE id = ?"
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val caracteristica = BCaracteristica(
                resultadoConsulta.getInt(0),
                resultadoConsulta.getString(1),
                resultadoConsulta.getString(2)
            )
            resultadoConsulta.close()
            db.close()
            caracteristica
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    fun consultarCaracteristicasPorPlanetaId(planetaId: Int): List<BCaracteristica> {
        val listaCaracteristicas = mutableListOf<BCaracteristica>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM CARACTERISTICA WHERE planetaId = ?"
        val parametrosConsulta = arrayOf(planetaId.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val caracteristica = BCaracteristica(
                    resultadoConsulta.getInt(0),  // id
                    resultadoConsulta.getString(1),  // titulo
                    resultadoConsulta.getString(2)  // descripcion
                )
                listaCaracteristicas.add(caracteristica)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaCaracteristicas
    }

}
