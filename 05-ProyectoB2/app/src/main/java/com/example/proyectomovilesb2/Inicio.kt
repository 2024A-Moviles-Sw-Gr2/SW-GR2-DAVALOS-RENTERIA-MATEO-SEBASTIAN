package com.example.proyectomovilesb2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.proyectomovilesb2.adapters.ReviewAdapter
import com.example.proyectomovilesb2.entities.BReseña
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperReseña
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperUsuario

class Inicio : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var resenaAdapter: ReviewAdapter
    private lateinit var sqliteHelperReseña: ESqliteHelperReseña
    private lateinit var sqliteHelperUsuario: ESqliteHelperUsuario
    private lateinit var btnPublicarResena: Button

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Actualiza la lista de reseñas
            loadResenas()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Inicializa RecyclerView usando findViewById
        recyclerView = findViewById(R.id.recyclerUserprofile)
        setupRecyclerView()
        loadResenas()
        setupBottomNavigation()

        btnPublicarResena = findViewById(R.id.btnPublicarResena)

        btnPublicarResena.setOnClickListener {
            // Aquí va el código para manejar la acción de publicar una nueva reseña
            val intent = Intent(this, PublicarResena::class.java)
            resultLauncher.launch(intent) // Usa resultLauncher en lugar de startActivity
        }


    }

    private fun setupRecyclerView() {
        // Inicializa los helpers
        sqliteHelperReseña = ESqliteHelperReseña(this)
        sqliteHelperUsuario = ESqliteHelperUsuario(this)

        resenaAdapter = ReviewAdapter(emptyList(), sqliteHelperUsuario)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Inicio)
            adapter = resenaAdapter
        }
    }

    private fun loadResenas() {
        val reseñas = sqliteHelperReseña.obtenerReseñas()
        resenaAdapter.updateReviews(reseñas)
    }

    private fun setupBottomNavigation() {
        // Implementa la lógica para la navegación inferior aquí
    }


}