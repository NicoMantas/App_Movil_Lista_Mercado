package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etProductName = findViewById<EditText>(R.id.etProductName)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val etQuantity = findViewById<EditText>(R.id.etQuantity)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val btnAddProduct = findViewById<MaterialButton>(R.id.btnAddProduct)
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
            val productName = etProductName.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString()
            val quantity = etQuantity.text.toString().trim()

            if (productName.isEmpty() || quantity.isEmpty()) {
                if (productName.isEmpty()) {
                    etProductName.error = "Ingresa nombre del producto"
                }
                if (quantity.isEmpty()) {
                    etQuantity.error = "Ingresa cantidad"
                }
                return@setOnClickListener
            }

            Toast.makeText(this, "Producto '$productName' agregado a la lista", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Ya estás en Agregar Producto", Toast.LENGTH_SHORT).show()
        }
    }
}
