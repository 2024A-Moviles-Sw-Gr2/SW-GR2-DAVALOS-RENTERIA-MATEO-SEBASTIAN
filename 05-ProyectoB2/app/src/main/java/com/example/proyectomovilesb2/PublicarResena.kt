package com.example.proyectomovilesb2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperReseña

class PublicarResena : AppCompatActivity() {
    private lateinit var dbHelper: ESqliteHelperReseña

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar_resena)

        dbHelper = ESqliteHelperReseña(this)

        // Configura el botón para crear una reseña
        val btnPublicar = findViewById<Button>(R.id.btnPublicar)
        btnPublicar.setOnClickListener {
            val titulo = findViewById<EditText>(R.id.etEntertitleOne).text.toString()
            val descripcion = findViewById<EditText>(R.id.etWritereviewOne).text.toString()
            val categoria = findViewById<EditText>(R.id.categoryInput).text.toString()
            val nombreCategoriaPelicula =
                ""// Asigna según tu lógica
             val likes = 0// Asigna según tu lógica
            val idUsuario = 1// Asigna el ID del usuario actual
            if (descripcion.isNotEmpty() && categoria.isNotEmpty() && descripcion.isNotEmpty()) {
                val exito = dbHelper.crearReseña(
                    idUsuario,
                    titulo,
                    descripcion,
                    categoria,
                    "",
                    nombreCategoriaPelicula,
                    likes
                )
                if (exito) {
                    Toast.makeText(this, "Reseña creada exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    setResult(RESULT_OK) // Devuelve resultado OK
                    finish() // Cierra la actividad
                } else {
                    Toast.makeText(this, "Error al crear la reseña", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun limpiarCampos() {
        findViewById<EditText>(R.id.etWritereviewOne).text.clear()
        findViewById<EditText>(R.id.categoryInput).text.clear()
        findViewById<EditText>(R.id.etEntertitleOne).text.clear()
    }


}