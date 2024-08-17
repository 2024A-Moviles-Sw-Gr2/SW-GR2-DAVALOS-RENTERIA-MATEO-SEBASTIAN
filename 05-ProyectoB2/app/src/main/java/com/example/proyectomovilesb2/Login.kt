package com.example.proyectomovilesb2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperUsuario

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIngresar: Button
    private lateinit var dbHelper: ESqliteHelperUsuario
    private lateinit var btnRegistrarse: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = ESqliteHelperUsuario(this)

        // Referencias a los elementos de la vista
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarse = findViewById(R.id.btnRegistrate)

        // Configurar el botón para manejar el inicio de sesión
        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validar que los campos no estén vacíos
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Intentar iniciar sesión
                val usuario = dbHelper.iniciarSesion(email, password)
                if (usuario != null) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Inicio::class.java)
                    startActivity(intent)
                    finish() // Finaliza la actividad actual para que no se pueda volver a ella con el botón atrás
                } else {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Registrarse::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}