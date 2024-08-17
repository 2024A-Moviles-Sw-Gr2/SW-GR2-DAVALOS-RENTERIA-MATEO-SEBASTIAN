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
import com.example.deber_02.adaptadores.PlanetaAdapter
import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.datos.BDatosMemoriaPlaneta
import com.example.deber_02.helpers.ESqliteHelperCaracteristica
import com.example.deber_02.helpers.ESqliteHelperPlaneta
import com.example.deber_02.mapa.Ubicacion
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adaptador: PlanetaAdapter
    private var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inicializarBd()
        initViews()
        setupListView()
        setupWindowInsets()
        setupButtons()


    }

    private fun inicializarBd(){
        //Inicializar base de datos
        EBaseDeDatos.tablaPlaneta = ESqliteHelperPlaneta(
            this
        )

        EBaseDeDatos.tablaCaracteristica = ESqliteHelperCaracteristica(
            this
        )
    }

    private fun initViews() {
        listView = findViewById(R.id.lv_planeta)
    }

    private fun setupListView() {
        adaptador = PlanetaAdapter(this, EBaseDeDatos.tablaPlaneta!!.obtenerTodosLosPlanetas())
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
        val botonCrear = findViewById<Button>(R.id.btn_crear_planeta)
        val btnUbicacion1: Button = findViewById(R.id.btn_ubicacion1)
        val btnUbicacion2: Button = findViewById(R.id.btn_ubicacion2)
        val btnUbicacion3: Button = findViewById(R.id.btn_ubicacion3)

        botonCrear.setOnClickListener {
            irActividad(PlanetasCrear::class.java, -1)
        }



        // Configurar OnClickListeners
        btnUbicacion1.setOnClickListener {
            irActividad(Map::class.java, ubicacion = "Ubicacion1")
        }

        btnUbicacion2.setOnClickListener {
            irActividad(Map::class.java, ubicacion = "Ubicacion1")
        }

        btnUbicacion3.setOnClickListener {
            irActividad(Map::class.java, ubicacion = "Ubicacion1")
        }
    }

    private fun irActividad(clase: Class<*>, planetaId: Int? = null, ubicacion: String? = null) {
        val intent = Intent(this, clase).apply {
            // Agregar "planeta_id" solo si no es nulo
            planetaId?.let { putExtra("planeta_id", it) }

            // Agregar "ubicacion" solo si no es nulo ni vacío
            ubicacion?.takeIf { it.isNotBlank() }?.let { putExtra("ubicacion", it) }
        }
        startActivity(intent)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_planeta, menu)
        posicionItemSeleccionado = (menuInfo as AdapterView.AdapterContextMenuInfo).position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val planetaId = info.targetView.tag as Int

        return when (item.itemId) {
            R.id.menu_planeta_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(PlanetasCrear::class.java, planetaId)
                true
            }
            R.id.menu_planeta_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                EBaseDeDatos.tablaPlaneta!!.eliminarPlaneta(planetaId)
                mostrarSnackbar("Planeta con id: $planetaId eliminado")
                irActividad(MainActivity::class.java, -1)
                true
            }
            R.id.menu_planeta_ver -> {
                mostrarSnackbar("Características $posicionItemSeleccionado")
                irActividad(Caracteristicas::class.java, planetaId)
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