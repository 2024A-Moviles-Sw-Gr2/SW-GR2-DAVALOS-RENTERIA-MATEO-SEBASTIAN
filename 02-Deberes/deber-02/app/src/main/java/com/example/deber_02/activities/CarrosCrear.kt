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
import com.example.deber_02.datos.BDatosMemoriaCarro
import com.example.deber_02.modelos.BCarro
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CarrosCrear : AppCompatActivity() {

    private lateinit var marcaEditText: EditText
    private lateinit var modeloEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var precioEditText: EditText
    private lateinit var estadoEditText: EditText
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carros_crear)
        setupViews()
        setupInsets()

        val marcaCarro = intent.getStringExtra("marca_carro")

        botonGuardar.setOnClickListener {
            handleSaveClick(marcaCarro)
        }
    }

    private fun setupViews() {
        marcaEditText = findViewById(R.id.txt_marca_carro)
        modeloEditText = findViewById(R.id.txt_modelo_carro)
        yearEditText = findViewById(R.id.txt_year_carro)
        precioEditText = findViewById(R.id.txt_precio_carro)
        estadoEditText = findViewById(R.id.txt_estado_carro)
        botonGuardar = findViewById(R.id.btn_guardar_carro)
        container = findViewById(R.id.cl_crear_carro)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(marcaCarro: String?) {
        val marca = marcaEditText.text.toString()
        val modelo = modeloEditText.text.toString()
        val year = yearEditText.text.toString().toIntOrNull()
        val precio = precioEditText.text.toString().toDoubleOrNull()
        val estado = estadoEditText.text.toString()

        if (validateInputs(marca, modelo, year, precio, estado)) {
            val respuesta = if (marcaCarro.isNullOrEmpty()) {
                crearNuevoCarro(marca, modelo, year!!, precio!!, estado)
            } else {
                actualizarCarroExistente(marcaCarro, modelo, year!!, precio!!, estado)
            }

            if (respuesta) {
                irActividad(Carros::class.java)
            } else {
                mostrarSnackBar("Error al guardar el Carro.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.\n Marca ${marca}, Modelo ${modelo}, Año ${year}, Precio ${precio}, Estado ${estado}")
        }
    }

    private fun validateInputs(marca: String, modelo: String, year: Int?, precio: Double?, estado: String): Boolean {
        return marca.isNotEmpty() && modelo.isNotEmpty() && year != null && precio != null && estado.isNotEmpty()
    }

    private fun crearNuevoCarro(marca: String, modelo: String, year: Int, precio: Double, estado: String): Boolean {
        val nuevoCarro = BCarro(marca, modelo, year, precio, estado)
        return BDatosMemoriaCarro.crearCarro(nuevoCarro)
    }

    private fun actualizarCarroExistente(marcaCarro: String, modelo: String, year: Int, precio: Double, estado: String): Boolean {
        val carroExistente = BDatosMemoriaCarro.obtenerCarroPorMarca(marcaCarro)
        return if (carroExistente != null) {
            val carroActualizado = BCarro(marcaCarro, modelo, year, precio, estado)
            BDatosMemoriaCarro.actualizarCarro(marcaCarro, carroActualizado)
        } else {
            false
        }
    }

    private fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
