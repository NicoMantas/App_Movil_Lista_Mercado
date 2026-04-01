package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class CreateListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etListName = findViewById<EditText>(R.id.etListName)
        val btnAddProduct = findViewById<MaterialButton>(R.id.btnAddProduct)
        val btnConfirmCreate = findViewById<MaterialButton>(R.id.btnConfirmCreate)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        val bottomBarBackground = bottomAppBar.background as? MaterialShapeDrawable
        bottomBarBackground?.let {
            val currentModel = it.shapeAppearanceModel
            it.shapeAppearanceModel = currentModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 40f)
                .setTopRightCorner(CornerFamily.ROUNDED, 40f)
                .build()
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("LIST_NAME", etListName.text.toString())
            intent.putExtra("PRODUCTS", arrayListOf<Product>())
            startActivity(intent)
        }

        btnConfirmCreate.setOnClickListener {
            val listName = etListName.text.toString().trim()
            if (listName.isEmpty()) {
                etListName.error = "Ingresa un nombre para la lista"
                return@setOnClickListener
            }
            Toast.makeText(this, "Lista '$listName' creada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeMainActivity::class.java))
            finish()
        }

        bottomNav.selectedItemId = R.id.item_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    startActivity(Intent(this, HomeMainActivity::class.java))
                    finish()
                    true
                }
                R.id.item_search -> {
                    Toast.makeText(this, "Abriendo buscador...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item_history -> {
                    startActivity(Intent(this, CreditsActivity::class.java))
                    finish()
                    true
                }
                R.id.item_profile -> {
                    Toast.makeText(this, "Tu perfil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        fab.setOnClickListener {
            Toast.makeText(this, "Ya estas en Crear Lista", Toast.LENGTH_SHORT).show()
        }
    }
}
