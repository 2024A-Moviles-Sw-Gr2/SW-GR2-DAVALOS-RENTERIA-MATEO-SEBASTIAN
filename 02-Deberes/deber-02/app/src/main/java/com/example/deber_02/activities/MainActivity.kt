package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.adaptadores.ConcesionarioAdapter
import com.example.deber_02.datos.BDatosMemoriaConcesionario
import com.example.deber_02.modelos.BConcesionario
import com.google.android.material.snackbar.Snackbar

class MainActivity: AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adaptador: ConcesionarioAdapter
    private var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initViews()
        setupListView()
        setupWindowInsets()
        setupButtons()
    }

    private fun initViews() {
        listView = findViewById(R.id.lv_concesionarios)
    }

    private fun setupListView() {
        adaptador = ConcesionarioAdapter(this, BDatosMemoriaConcesionario.obtenerConcesionarios())
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupButtons() {
        val botonCrear = findViewById<Button>(R.id.btn_crear_concesionario)
        botonCrear.setOnClickListener {
            irActividad(ConcesionariosCrear::class.java, null)
        }
    }

    private fun irActividad(clase: Class<*>, nombreConcesionario: String?) {
        val intent = Intent(this, clase).apply {
            putExtra("nombre_concesionario", nombreConcesionario)
        }
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_concesionario, menu)
        posicionItemSeleccionado = (menuInfo as AdapterView.AdapterContextMenuInfo).position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val concesionario = info.targetView.tag as String

        return when (item.itemId) {
            R.id.menu_concesionario_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(ConcesionariosCrear::class.java, concesionario)
                true
            }
            R.id.menu_concesionario_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                BDatosMemoriaConcesionario.eliminarConcesionario(concesionario)
                mostrarSnackbar("Concesionario con nombre: ${concesionario} eliminado")
                irActividad(MainActivity::class.java, null)
                true
            }
            R.id.menu_concesionario_ver -> {
                mostrarSnackbar("Ver Carros $posicionItemSeleccionado")
                irActividad(Carros::class.java, concesionario)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        ).show()
    }
}
