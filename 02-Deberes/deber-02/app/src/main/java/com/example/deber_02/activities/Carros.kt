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
import com.example.deber_02.CarroAdapter
import com.example.deber_02.datos.BDatosMemoriaCarro
import com.example.deber_02.modelos.BCarro
import com.google.android.material.snackbar.Snackbar

class Carros : AppCompatActivity() {
    private var posicionItemSeleccionado = -1
    private var carroMarca: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carros)
        setupWindowInsets()
        println("Antes")
        carroMarca = intent.getStringExtra("carro_marca")
        println("Despues")
        setupListView()
        setupButtons()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fl_carros)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListView() {
        val listView = findViewById<ListView>(R.id.lv_listCarros)
        val adaptador = CarroAdapter(this, BDatosMemoriaCarro.obtenerCarros())
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() // Notificar las actualizaciones a la interfaz
        registerForContextMenu(listView)
    }

    private fun setupButtons() {
        val botonCrear = findViewById<Button>(R.id.btn_crear_carros)
        botonCrear.setOnClickListener {
            irActividad(CarrosCrear::class.java, null)
        }

        val botonRegresar = findViewById<Button>(R.id.btn_regresar)
        botonRegresar.setOnClickListener {
            irActividad(MainActivity::class.java, null)
        }
    }

    private fun irActividad(clase: Class<*>, marca: String?) {
        val intent = Intent(this, clase).apply {
            putExtra("carro_marca", marca)
        }
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_carro, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
        carroMarca = (info.targetView.tag as? BCarro)?.marca
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_carro_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(CarrosCrear::class.java, carroMarca)
                true
            }

            R.id.menu_carro_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                eliminarCarro(carroMarca)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminarCarro(marca: String?) {
        marca?.let {
            BDatosMemoriaCarro.eliminarCarro(marca)
            mostrarSnackbar("Carro con marca: $marca eliminada")
            irActividad(Carros::class.java, null)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.fl_carros), texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
