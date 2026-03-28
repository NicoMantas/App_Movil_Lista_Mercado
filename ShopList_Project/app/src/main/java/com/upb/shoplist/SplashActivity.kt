package com.upb.shoplist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btnRegistrate = findViewById<AppCompatButton>(R.id.btnRegistrate)
        val btnIniciarSesion = findViewById<AppCompatButton>(R.id.btnIniciarSesion)

        btnRegistrate.setOnClickListener {
            Toast.makeText(this, "Ir a Registro", Toast.LENGTH_SHORT).show()
        }

        btnIniciarSesion.setOnClickListener {
            Toast.makeText(this, "Ir a Inicio de Sesión", Toast.LENGTH_SHORT).show()
        }
    }

}