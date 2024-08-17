package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.helpers.ESqliteHelperCaracteristica
import com.google.android.material.snackbar.Snackbar

class CaracteristicasCrear : AppCompatActivity() {

    private lateinit var descripcionEditText: EditText
    private lateinit var tituloEditText: EditText
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_caracteristicas_crear)
        setupViews()
        setupInsets()

        val caracteristicaId = intent.getIntExtra("caracteristica_id", -1)


        botonGuardar.setOnClickListener {
            handleSaveClick(caracteristicaId)
        }
    }

    private fun setupViews() {
        descripcionEditText = findViewById(R.id.txt_descripcion_caracteristica)
        tituloEditText = findViewById(R.id.txt_titulo)
        botonGuardar = findViewById(R.id.btn_guardar_caracteristica)
        container = findViewById(R.id.cl_crear_caracteristica)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(caracteristicaId: Int) {
        val titulo = tituloEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()
        val planetaId = intent.getIntExtra("planeta_id",-1)

        if (descripcion.isNotEmpty() && titulo.isNotEmpty()) {
            val respuesta = if (caracteristicaId == -1) {
                crearNuevaCaracteristica(titulo, descripcion, planetaId) // Asumiendo que planetaId es 0
            } else {
                actualizarCaracteristicaExistente(caracteristicaId, titulo, descripcion)
            }

            if (respuesta) {
                irActividad(Caracteristicas::class.java, planetaId)
            } else {
                mostrarSnackBar("Error al guardar la Característica.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.")
        }
    }

    private fun crearNuevaCaracteristica(titulo: String, descripcion: String, planetaId: Int): Boolean {
        return EBaseDeDatos.tablaCaracteristica?.crearCaracteristica(titulo, descripcion, planetaId) ?: false
    }

    private fun actualizarCaracteristicaExistente(caracteristicaId: Int, titulo: String, descripcion: String): Boolean {
        return EBaseDeDatos.tablaCaracteristica?.actualizarCaracteristica(caracteristicaId, titulo, descripcion) ?: false
    }

    private fun irActividad(clase: Class<*>, planetaId: Int) {
        val intent = Intent(this, clase).apply {
            putExtra("planeta_id", planetaId)
        }
        startActivity(intent)
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
