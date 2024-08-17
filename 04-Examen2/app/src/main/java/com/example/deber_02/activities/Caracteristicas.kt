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
import com.example.deber_02.adaptadores.CaracteristicaAdapter
import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.datos.BDatosMemoriaCaracteristica
import com.google.android.material.snackbar.Snackbar
class Caracteristicas : AppCompatActivity() {
    private var posicionItemSeleccionado = -1
    private var planetaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_caracteristicas)
        setupWindowInsets()

        planetaId = intent.getIntExtra("planeta_id", -1)
        setupListView()
        setupButtons()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fl_caracteristicas)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListView() {
        val listView = findViewById<ListView>(R.id.lv_listCaracteristicas)
        val adaptador =
            CaracteristicaAdapter(this, EBaseDeDatos.tablaCaracteristica!!.consultarCaracteristicasPorPlanetaId(planetaId))
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() // notificar las actualizaciones a la interfaz
        registerForContextMenu(listView)
    }

    private fun setupButtons() {
        val botonCrear = findViewById<Button>(R.id.btn_crear_caracteristicas)
        botonCrear.setOnClickListener {
            irActividad(CaracteristicasCrear::class.java, planetaId, null)
        }

        val botonRegresar = findViewById<Button>(R.id.btn_regresar)
        botonRegresar.setOnClickListener {
            irActividad(MainActivity::class.java, -1, null)
        }
    }

    private fun irActividad(clase: Class<*>, planetaId: Int, caracteristicaId: Int?) {
        val intent = Intent(this, clase).apply {
            putExtra("planeta_id", planetaId)
            caracteristicaId?.let {
                putExtra("caracteristica_id", it)
            }
        }
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_caracteristica, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val caracteristicaId = info.targetView.tag as Int

        return when (item.itemId) {
            R.id.menu_caracteristica_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(CaracteristicasCrear::class.java, planetaId, caracteristicaId)
                true
            }

            R.id.menu_caracteristica_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                eliminarCaracteristica(caracteristicaId)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminarCaracteristica(caracteristicaId: Int?) {
        if (caracteristicaId != null) {
            EBaseDeDatos.tablaCaracteristica!!.eliminarCaracteristica(caracteristicaId)
            mostrarSnackbar("Característica con id: $caracteristicaId eliminada")
        }
        irActividad(Caracteristicas::class.java, planetaId, null)
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.fl_caracteristicas), texto, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}
