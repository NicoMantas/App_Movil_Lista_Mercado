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

class EditProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val etProductName = findViewById<EditText>(R.id.etProductName)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val etQuantity = findViewById<EditText>(R.id.etQuantity)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val btnSaveProduct = findViewById<MaterialButton>(R.id.btnSaveProduct)
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

        val listId = intent.getStringExtra("LIST_ID") ?: java.util.UUID.randomUUID().toString()
        val listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista"
        val productId = intent.getStringExtra("PRODUCT_ID")
        val products = intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product> ?: arrayListOf()

        val productIndex = products.indexOfFirst { it.id == productId }
        if (productIndex < 0) {
            Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show()
            goBackToDetails(listId, listName, products)
            return
        }

        val selectedProduct = products[productIndex]
        etProductName.setText(selectedProduct.name)
        etQuantity.setText(selectedProduct.quantity.toString())
        etPrice.setText(selectedProduct.price.toString())

        val categories = resources.getStringArray(R.array.categories)
        val categoryIndex = categories.indexOf(selectedProduct.category)
        if (categoryIndex >= 0) {
            spinnerCategory.setSelection(categoryIndex)
        }

        btnBack.setOnClickListener {
            goBackToDetails(listId, listName, products)
        }

        btnSaveProduct.setOnClickListener {
            val productName = etProductName.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString()
            val quantityText = etQuantity.text.toString().trim()
            val priceText = etPrice.text.toString().trim()

            if (productName.isEmpty()) {
                etProductName.error = "Ingresa nombre del producto"
                return@setOnClickListener
            }
            if (quantityText.isEmpty()) {
                etQuantity.error = "Ingresa cantidad"
                return@setOnClickListener
            }

            val quantity = quantityText.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                etQuantity.error = "Ingresa una cantidad válida"
                return@setOnClickListener
            }

            val price = priceText.toDoubleOrNull() ?: 0.0

            products[productIndex] = selectedProduct.copy(
                name = productName,
                category = category,
                quantity = quantity,
                price = price
            )

            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
            goBackToDetails(listId, listName, products)
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
            Toast.makeText(this, "Edición de producto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goBackToDetails(listId: String, listName: String, products: ArrayList<Product>) {
        val intent = Intent(this, ListDetailsActivity::class.java)
        intent.putExtra("LIST_ID", listId)
        intent.putExtra("LIST_NAME", listName)
        intent.putExtra("PRODUCTS", products)
        startActivity(intent)
        finish()
    }
}