package com.example.proyectomovilesb2.sqlhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectomovilesb2.entities.BReseña
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


class ESqliteHelperReseña(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "reseñas",
    null,
    3
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaReseña = """
            CREATE TABLE RESEÑA(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            id_usuario INTEGER,
            titulo TEXT,
            descripcion TEXT,
            nombre_categoria_reseña VARCHAR(50),
            nombre_pelicula VARCHAR(50),
            nombre_categoria_pelicula VARCHAR(50),
            likes INTEGER,
            fecha TEXT
        )   
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaReseña)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            val scriptSQLActualizarTablaReseña = """
            ALTER TABLE RESEÑA ADD COLUMN fecha TEXT
        """.trimIndent()
            db?.execSQL(scriptSQLActualizarTablaReseña)
        }
        if (oldVersion < 3) {
            val scriptSQLActualizarTablaReseña = """
            ALTER TABLE RESEÑA ADD COLUMN titulo TEXT
        """.trimIndent()
            db?.execSQL(scriptSQLActualizarTablaReseña)
        }
    }

    // Crear Reseña
    fun crearReseña(
        id_usuario: Int,
        titulo: String,
        descripcion: String,
        nombre_categoria_reseña: String,
        nombre_pelicula: String,
        nombre_categoria_pelicula: String,
        likes: Int
    ): Boolean {
        val db = writableDatabase
        val valoresReseña = ContentValues()
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaActual = formatoFecha.format(Date())

        valoresReseña.put("id_usuario", id_usuario)
        valoresReseña.put("titulo", titulo)
        valoresReseña.put("descripcion", descripcion)
        valoresReseña.put("nombre_categoria_reseña", nombre_categoria_reseña)
        valoresReseña.put("nombre_pelicula", nombre_pelicula)
        valoresReseña.put("nombre_categoria_pelicula", nombre_categoria_pelicula)
        valoresReseña.put("likes", likes)
        valoresReseña.put("fecha", fechaActual)

        val resultado = db.insert("RESEÑA", null, valoresReseña)
        db.close()
        return resultado != -1L
    }

    // Eliminar Reseña
    fun eliminarReseña(id: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(id.toString())
        val resultadoEliminar = db.delete("RESEÑA", "id=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    // Actualizar Reseña
    fun actualizarReseña(
        id: Int,
        titulo: String,
        descripcion: String,
        nombre_categoria_reseña: String,
        nombre_pelicula: String,
        nombre_categoria_pelicula: String,
        likes: Int
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("titulo", titulo)
        valores.put("descripcion", descripcion)
        valores.put("nombre_categoria_reseña", nombre_categoria_reseña)
        valores.put("nombre_pelicula", nombre_pelicula)
        valores.put("nombre_categoria_pelicula", nombre_categoria_pelicula)
        valores.put("likes", likes)
        val fechaActual =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        valores.put("fecha", fechaActual)

        val parametros = arrayOf(id.toString())
        val resultado = db.update("RESEÑA", valores, "id=?", parametros)
        db.close()
        return resultado != -1
    }

    // Consultar Reseña por ID
    fun consultarReseñaPorID(id: Int): BReseña? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM RESEÑA WHERE id = ?"
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val reseña = BReseña(
                resultadoConsulta.getInt(0), // id
                resultadoConsulta.getInt(1), // id_usuario
                resultadoConsulta.getString(2), // descripcion
                resultadoConsulta.getString(3), // nombre_categoria_reseña
                resultadoConsulta.getString(4), // nombre_pelicula
                resultadoConsulta.getString(5), // nombre_categoria_pelicula
                resultadoConsulta.getInt(6), // likes
                resultadoConsulta.getString(7), // fecha
                resultadoConsulta.getString(8) // titulo

            )
            resultadoConsulta.close()
            db.close()
            reseña
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    // Consultar todas las Reseñas por Usuario
    fun consultarReseñasPorUsuarioId(id_usuario: Int): List<BReseña> {
        val listaReseñas = mutableListOf<BReseña>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM RESEÑA WHERE id_usuario = ?"
        val parametrosConsulta = arrayOf(id_usuario.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val reseña = BReseña(
                    resultadoConsulta.getInt(0), // id
                    resultadoConsulta.getInt(1), // id_usuario
                    resultadoConsulta.getString(2), // descripcion
                    resultadoConsulta.getString(3), // nombre_categoria_reseña
                    resultadoConsulta.getString(4), // nombre_pelicula
                    resultadoConsulta.getString(5), // nombre_categoria_pelicula
                    resultadoConsulta.getInt(6), // likes
                    resultadoConsulta.getString(7), // fecha
                    resultadoConsulta.getString(8) // titulo
                )
                listaReseñas.add(reseña)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaReseñas
    }

    fun obtenerReseñas(): List<BReseña> {
        val listaReseñas = mutableListOf<BReseña>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM RESEÑA"
        val resultadoConsulta = db.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val reseña = BReseña(
                    resultadoConsulta.getInt(0), // id
                    resultadoConsulta.getInt(1), // id_usuario
                    resultadoConsulta.getString(2), // descripcion
                    resultadoConsulta.getString(3), // nombre_categoria_reseña
                    resultadoConsulta.getString(4), // nombre_pelicula
                    resultadoConsulta.getString(5), // nombre_categoria_pelicula
                    resultadoConsulta.getInt(6), // likes
                    resultadoConsulta.getString(7), // fecha
                    resultadoConsulta.getString(8) // titulo

                )
                listaReseñas.add(reseña)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaReseñas
    }
}