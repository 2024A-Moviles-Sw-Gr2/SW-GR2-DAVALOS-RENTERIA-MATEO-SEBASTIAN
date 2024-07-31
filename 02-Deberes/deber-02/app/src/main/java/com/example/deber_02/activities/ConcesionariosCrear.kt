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
import com.example.deber_02.datos.BDatosMemoriaConcesionario
import com.example.deber_02.modelos.BConcesionario
import com.google.android.material.snackbar.Snackbar

class ConcesionariosCrear : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var ubicacionEditText: EditText
    private lateinit var numeroEmpleadosEditText: EditText
    private lateinit var isOpenCheckBox: CheckBox
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_concesionarios_crear)
        setupViews()
        setupInsets()

        // Obtener el nombre del concesionario si se está editando
        val concesionarioNombre = intent.getStringExtra("concesionario_nombre")

        // Cargar los datos si el concesionario ya existe
        if (concesionarioNombre != null) {
            cargarDatos(concesionarioNombre)
        }

        botonGuardar.setOnClickListener {
            handleSaveClick(concesionarioNombre)
        }
    }

    private fun setupViews() {
        nombreEditText = findViewById(R.id.txt_nombre_concesionario)
        ubicacionEditText = findViewById(R.id.txt_ubicacion_concesionario)
        numeroEmpleadosEditText = findViewById(R.id.txt_numero_empleados)
        isOpenCheckBox = findViewById(R.id.chk_is_open)
        botonGuardar = findViewById(R.id.btn_guardar_concesionario)
        container = findViewById(R.id.cl_crear_concesionario)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(concesionarioNombre: String?) {
        val nombre = nombreEditText.text.toString()
        val ubicacion = ubicacionEditText.text.toString()
        val numeroEmpleados = numeroEmpleadosEditText.text.toString().toIntOrNull()
        val isOpen = isOpenCheckBox.isChecked

        if (validateInputs(nombre, ubicacion, numeroEmpleados)) {
            val respuesta = if (concesionarioNombre == null) {
                crearNuevoConcesionario(nombre, ubicacion, isOpen, numeroEmpleados!!)
            } else {
                actualizarConcesionarioExistente(concesionarioNombre, nombre, ubicacion, isOpen, numeroEmpleados!!)
            }

            if (respuesta) {
                irActividad(MainActivity::class.java)
            } else {
                mostrarSnackBar("Error al guardar el Concesionario.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.\n Nombre $nombre, Ubicación $ubicacion, Empleados $numeroEmpleados")
        }
    }

    private fun validateInputs(nombre: String, ubicacion: String, numeroEmpleados: Int?): Boolean {
        return nombre.isNotEmpty() && ubicacion.isNotEmpty() && numeroEmpleados != null
    }

    private fun crearNuevoConcesionario(nombre: String, ubicacion: String, isOpen: Boolean, numeroEmpleados: Int): Boolean {
        val nuevoConcesionario = BConcesionario(nombre, ubicacion, isOpen, numeroEmpleados)
        return BDatosMemoriaConcesionario.crearConcesionario(nuevoConcesionario)
    }

    private fun actualizarConcesionarioExistente(nombreAntiguo: String, nombre: String, ubicacion: String, isOpen: Boolean, numeroEmpleados: Int): Boolean {
        val nuevoConcesionario = BConcesionario(nombre, ubicacion, isOpen, numeroEmpleados)
        return BDatosMemoriaConcesionario.actualizarConcesionario(nombreAntiguo, nuevoConcesionario)
    }

    private fun cargarDatos(nombre: String) {
        val concesionario = BDatosMemoriaConcesionario.obtenerConcesionario(nombre)
        concesionario?.let {
            nombreEditText.setText(it.nombre)
            ubicacionEditText.setText(it.ubicacion)
            numeroEmpleadosEditText.setText(it.numeroEmpleados.toString())
            isOpenCheckBox.isChecked = it.isOpen
        }
    }

    private fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
