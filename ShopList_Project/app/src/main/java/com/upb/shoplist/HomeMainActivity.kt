package com.upb.shoplist

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class HomeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        // 1. Vinculación de Vistas
        val tvWelcomeUser = findViewById<TextView>(R.id.tvWelcomeUser)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        // --- CORRECCIÓN DE BORDES REDONDEADOS ---
        val bottomBarBackground = bottomAppBar.background as? MaterialShapeDrawable
        bottomBarBackground?.let {
            val currentModel = it.shapeAppearanceModel
            // Aplicamos 24dp (aprox 70f-80f dependiendo de la densidad) a las esquinas superiores
            it.shapeAppearanceModel = currentModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 40f)
                .setTopRightCorner(CornerFamily.ROUNDED, 40f)
                .build()
        }

        // 2. Lógica del Saludo Personalizado
        val userName = intent.getStringExtra("USER_NAME")
        tvWelcomeUser.text = if (!userName.isNullOrEmpty()) "Bienvenido, $userName!" else "Bienvenido, User!"

        // 3. Configuración del Menú de Navegación
        bottomNav.selectedItemId = R.id.item_home
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
            Toast.makeText(this, "Creando nueva lista...", Toast.LENGTH_SHORT).show()
        }
    }
}