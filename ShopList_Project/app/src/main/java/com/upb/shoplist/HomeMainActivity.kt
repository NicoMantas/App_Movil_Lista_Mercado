package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class HomeMainActivity : AppCompatActivity() {

    private lateinit var rvSavedLists: RecyclerView
    private lateinit var layoutEmptyState: android.view.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)

        // 1. Vinculación de Vistas
        val tvWelcomeUser = findViewById<TextView>(R.id.tvWelcomeUser)
        rvSavedLists = findViewById(R.id.rvSavedLists)
        layoutEmptyState = findViewById(R.id.layoutEmptyState)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        rvSavedLists.layoutManager = LinearLayoutManager(this)

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
                    startActivity(Intent(this, CreditsActivity::class.java))
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
            startActivity(Intent(this, CreateListActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadSavedLists()
    }

    private fun loadSavedLists() {
        val savedLists = ShoppingListStorage.getLists(this)
        layoutEmptyState.visibility = if (savedLists.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE

        rvSavedLists.adapter = SavedListsAdapter(savedLists) { list ->
            val intent = Intent(this, ListDetailsActivity::class.java)
            intent.putExtra("LIST_ID", list.id)
            intent.putExtra("LIST_NAME", list.name)
            intent.putExtra("PRODUCTS", ArrayList(list.products))
            startActivity(intent)
        }
    }
}