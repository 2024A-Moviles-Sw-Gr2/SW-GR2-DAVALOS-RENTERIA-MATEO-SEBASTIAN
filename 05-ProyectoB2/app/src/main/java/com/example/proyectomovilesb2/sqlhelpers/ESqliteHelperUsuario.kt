package com.example.proyectomovilesb2.sqlhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectomovilesb2.entities.BUsuario

class ESqliteHelperUsuario(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "usuarios",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaUsuario = """
            CREATE TABLE USUARIO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email VARCHAR(50),
                contra VARCHAR(50),
                nombre VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaUsuario)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario
    }

    // Crear Usuario
    fun crearUsuario(
        email: String,
        contra: String,
        nombre: String
    ): Boolean {
        val db = writableDatabase
        val valoresUsuario = ContentValues()
        valoresUsuario.put("email", email)
        valoresUsuario.put("contra", contra)
        valoresUsuario.put("nombre", nombre)

        val resultado = db.insert("USUARIO", null, valoresUsuario)
        db.close()
        return resultado != -1L
    }

    // Eliminar Usuario
    fun eliminarUsuario(id: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(id.toString())
        val resultadoEliminar = db.delete("USUARIO", "id=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    // Actualizar Usuario
    fun actualizarUsuario(
        id: Int,
        email: String,
        contra: String,
        nombre: String
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("email", email)
        valores.put("contra", contra)
        valores.put("nombre", nombre)

        val parametros = arrayOf(id.toString())
        val resultado = db.update("USUARIO", valores, "id=?", parametros)
        db.close()
        return resultado != -1
    }

    // Consultar Usuario por ID
    fun consultarUsuarioPorID(id: Int): BUsuario? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM USUARIO WHERE id = ?"
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val usuario = BUsuario(
                resultadoConsulta.getInt(0), // id
                resultadoConsulta.getString(1), // email
                resultadoConsulta.getString(2), // contra
                resultadoConsulta.getString(3)  // nombre
            )
            resultadoConsulta.close()
            db.close()
            usuario
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    // Consultar todos los Usuarios3
    fun consultarTodosLosUsuarios(): List<BUsuario> {
        val listaUsuarios = mutableListOf<BUsuario>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM USUARIO"
        val resultadoConsulta = db.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val usuario = BUsuario(
                    resultadoConsulta.getInt(0),  // id
                    resultadoConsulta.getString(1),  // email
                    resultadoConsulta.getString(2),  // contra
                    resultadoConsulta.getString(3)   // nombre
                )
                listaUsuarios.add(usuario)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaUsuarios
    }


    // Método para registrar un usuario (verifica si el email ya existe)
    fun registrarUsuario(email: String, contra: String, nombre: String): Boolean {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM USUARIO WHERE email = ?"
        val parametrosConsulta = arrayOf(email)
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            // Si ya existe un usuario con ese correo, no permitimos el registro
            resultadoConsulta.close()
            db.close()
            false
        } else {
            // Si no existe, creamos un nuevo usuario
            resultadoConsulta.close()
            db.close()
            crearUsuario(email, contra, nombre)
        }
    }

    // Método para iniciar sesión (verifica si el email y contraseña coinciden)
    fun iniciarSesion(email: String, contra: String): BUsuario? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM USUARIO WHERE email = ? AND contra = ?"
        val parametrosConsulta = arrayOf(email, contra)
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val usuario = BUsuario(
                resultadoConsulta.getInt(0),  // id
                resultadoConsulta.getString(1),  // email
                resultadoConsulta.getString(2),  // contra
                resultadoConsulta.getString(3)   // nombre
            )
            resultadoConsulta.close()
            db.close()
            usuario
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

}
