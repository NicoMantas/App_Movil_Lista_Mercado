package com.upb.shoplist

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnFinalizeRegister = findViewById<AppCompatButton>(R.id.btnFinalizeRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        btnBack.setOnClickListener {
            finish() // Regresa a la pantalla anterior
        }

        btnFinalizeRegister.setOnClickListener {
            Toast.makeText(this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show()
        }

        tvGoToLogin.setOnClickListener {
            // Aquí luego conectarás con LoginActivity
            Toast.makeText(this, "Ir a Iniciar Sesión", Toast.LENGTH_SHORT).show()
        }
    }
}