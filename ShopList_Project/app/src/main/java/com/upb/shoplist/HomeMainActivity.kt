package com.upb.shoplist

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)


        // 1. Vinculación de Vistas
        val tvWelcomeUser = findViewById<TextView>(R.id.tvWelcomeUser)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)

        // 2. Lógica del Saludo Personalizado
        // Intentamos obtener el nombre enviado desde el Login
        val userName = intent.getStringExtra("USER_NAME")

        if (!userName.isNullOrEmpty()) {
            tvWelcomeUser.text = "Bienvenido, $userName!"
        } else {
            tvWelcomeUser.text = "Bienvenido, User!"
        }

        // Esto hace que el Home aparezca seleccionado al arrancar
        bottomNav.selectedItemId = R.id.item_home

        // 3. Configuración del Menú de Navegación
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item_home -> {
                    Toast.makeText(this, "Ya estás en Inicio", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_search -> {
                    Toast.makeText(this, "Abriendo buscador...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_history -> {
                    Toast.makeText(this, "Cargando historial...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_profile -> {
                    Toast.makeText(this, "Tu perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // 4. Lógica del Botón Flotante (+)
        fab.setOnClickListener {
            // Aquí luego abriremos la nueva Activity para crear listas
            Toast.makeText(this, "Creando nueva lista...", Toast.LENGTH_SHORT).show()
        }
    }
}