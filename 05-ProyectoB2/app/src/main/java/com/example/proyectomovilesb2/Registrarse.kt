package com.example.proyectomovilesb2

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperUsuario

class Registrarse : AppCompatActivity() {

    private lateinit var dbHelper: ESqliteHelperUsuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = ESqliteHelperUsuario(this)

        findViewById<android.widget.Button>(R.id.btnRegistrarse).setOnClickListener {
            registrarUsuario()
        }

        findViewById<android.widget.LinearLayout>(R.id.regresar_registrarse).setOnClickListener {
            finish()
        }


    }

    private fun registrarUsuario() {

        val username = findViewById<EditText>(R.id.etUsuario123value).text.toString()
        val email = findViewById<EditText>(R.id.etEmail2).text.toString()
        val password = findViewById<EditText>(R.id.etContraseavalue).text.toString()
        val confirmarPassword = findViewById<EditText>(R.id.etInput).text.toString()
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos")
            return
        }

        if (password != confirmarPassword) {
            mostrarMensaje("Las contraseñas no coinciden")
            return
        }

        val registroExitoso = dbHelper.registrarUsuario(email, password, username)

        if (registroExitoso) {
            mostrarMensaje("Usuario registrado exitosamente")
            finish()
        } else {
            mostrarMensaje("El correo electrónico ya está en uso")
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}