package com.upb.shoplist

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        val tvWelcomeUser = findViewById<TextView>(R.id.tvWelcomeUser)

        // Intentamos obtener el nombre enviado desde el Login
        val userName = intent.getStringExtra("USER_NAME")

        if (!userName.isNullOrEmpty()) {
            tvWelcomeUser.text = "Bienvenido, $userName!"
        } else {
            tvWelcomeUser.text = "Bienvenido, User!"
        }
    }
}