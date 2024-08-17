package com.example.deber_02.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber_02.modelos.BPlaneta
import java.util.*

class ESqliteHelperPlaneta(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "planetas",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPlaneta = """
            CREATE TABLE PLANETA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                diametro DOUBLE,
                tieneSatelites INTEGER,
                fechaDescubrimiento LONG
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPlaneta)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario
    }

    fun crearPlaneta(
        nombre: String,
        diametro: Double,
        tieneSatelites: Boolean,
        fechaDescubrimiento: Date
    ): Boolean {
        val db = writableDatabase
        val valoresPlaneta = ContentValues()
        valoresPlaneta.put("nombre", nombre)
        valoresPlaneta.put("diametro", diametro)
        valoresPlaneta.put("tieneSatelites", if (tieneSatelites) 1 else 0)
        valoresPlaneta.put("fechaDescubrimiento", fechaDescubrimiento.time)

        val resultado = db.insert("PLANETA", null, valoresPlaneta)
        db.close()
        return resultado != -1L
    }

    fun eliminarPlaneta(id: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(id.toString())
        val resultadoEliminar = db.delete("PLANETA", "id=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    fun actualizarPlaneta(
        id: Int,
        nombre: String,
        diametro: Double,
        tieneSatelites: Boolean,
        fechaDescubrimiento: Date
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("nombre", nombre)
        valores.put("diametro", diametro)
        valores.put("tieneSatelites", if (tieneSatelites) 1 else 0)
        valores.put("fechaDescubrimiento", fechaDescubrimiento.time)

        val parametros = arrayOf(id.toString())
        val resultado = db.update("PLANETA", valores, "id=?", parametros)
        db.close()
        return resultado != -1
    }

    fun consultarPlanetaPorID(id: Int): BPlaneta? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM PLANETA WHERE id = ?"
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val planeta = BPlaneta(
                resultadoConsulta.getInt(0),
                resultadoConsulta.getString(1),
                resultadoConsulta.getDouble(2),
                resultadoConsulta.getInt(3) == 1,
                Date(resultadoConsulta.getLong(4))
            )
            resultadoConsulta.close()
            db.close()
            planeta
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    fun obtenerTodosLosPlanetas(): List<BPlaneta> {
        val listaPlanetas = mutableListOf<BPlaneta>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM PLANETA"
        val resultadoConsulta = db.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val planeta = BPlaneta(
                    resultadoConsulta.getInt(0),  // id
                    resultadoConsulta.getString(1),  // nombre
                    resultadoConsulta.getDouble(2),  // diametro
                    resultadoConsulta.getInt(3) == 1,  // tieneSatelites
                    Date(resultadoConsulta.getLong(4))  // fechaDescubrimiento
                )
                listaPlanetas.add(planeta)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaPlanetas
    }
}
