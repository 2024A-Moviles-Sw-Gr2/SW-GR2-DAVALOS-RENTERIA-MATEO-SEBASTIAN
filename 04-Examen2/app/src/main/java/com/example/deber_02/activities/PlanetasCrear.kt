package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R

import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.modelos.BPlaneta
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class PlanetasCrear : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var diametroEditText: EditText
    private lateinit var fechaDescubrimientoEditText: EditText
    private lateinit var tieneSatelitesCheckBox: CheckBox
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_planetas_crear)
        setupViews()
        setupInsets()

        val planetaId = intent.getIntExtra("planeta_id", -1)

        botonGuardar.setOnClickListener {
            handleSaveClick(planetaId)
        }
    }

    private fun setupViews() {
        nombreEditText = findViewById(R.id.txt_nombre_planeta)
        diametroEditText = findViewById(R.id.txt_diametro_planeta)
        fechaDescubrimientoEditText = findViewById(R.id.txt_fecha_descubrimiento)
        tieneSatelitesCheckBox = findViewById(R.id.chk_tiene_satelites)
        botonGuardar = findViewById(R.id.btn_guardar_planeta)
        container = findViewById(R.id.cl_crear_planeta)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(planetaId: Int) {
        val nombre = nombreEditText.text.toString()
        val diametro = diametroEditText.text.toString().toDoubleOrNull()
        val fechaDescubrimientoStr = fechaDescubrimientoEditText.text.toString()
        val tieneSatelites = tieneSatelitesCheckBox.isChecked

        val fechaDescubrimiento = parseFecha(fechaDescubrimientoStr)

        if (validateInputs(nombre, diametro, fechaDescubrimiento)) {
            val respuesta = if (planetaId == -1) {
                crearNuevoPlaneta(nombre, diametro!!, tieneSatelites, fechaDescubrimiento!!)
            } else {
                actualizarPlanetaExistente(planetaId, nombre, diametro!!, tieneSatelites, fechaDescubrimiento!!)
            }

            if (respuesta) {
                irActividad(MainActivity::class.java)
            } else {
                mostrarSnackBar("Error al guardar el Planeta.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.\n Nombre ${nombre}, Diametro ${diametro}, Fecha ${fechaDescubrimiento}\n" +
                    "${nombre.isNotEmpty()} ${diametro != null} ${fechaDescubrimiento != null}")
        }
    }

    private fun parseFecha(fechaStr: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(fechaStr)
        } catch (e: Exception) {
            null
        }
    }

    private fun validateInputs(nombre: String, diametro: Double?, fecha: Date?): Boolean {
        return nombre.isNotEmpty() && diametro != null && fecha != null
    }

    private fun crearNuevoPlaneta(nombre: String, diametro: Double, tieneSatelites: Boolean, fechaDescubrimiento: Date): Boolean {
        // Insertar el nuevo planeta en la base de datos usando ESqliteHelperPlaneta
        return EBaseDeDatos.tablaPlaneta?.crearPlaneta(nombre, diametro, tieneSatelites, fechaDescubrimiento) ?: false
    }

    private fun actualizarPlanetaExistente(planetaId: Int, nombre: String, diametro: Double, tieneSatelites: Boolean, fechaDescubrimiento: Date): Boolean {
        // Actualizar el planeta existente en la base de datos usando ESqliteHelperPlaneta
        return EBaseDeDatos.tablaPlaneta?.actualizarPlaneta(planetaId, nombre, diametro, tieneSatelites, fechaDescubrimiento) ?: false
    }

    private fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
