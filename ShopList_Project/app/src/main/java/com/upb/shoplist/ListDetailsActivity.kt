package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import java.util.UUID

class ListDetailsActivity : AppCompatActivity() {

    private lateinit var listId: String
    private lateinit var listName: String
    private val products = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private lateinit var tvTotalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_details)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val tvListName = findViewById<TextView>(R.id.tvListName)
        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        val tvEmptyState = findViewById<TextView>(R.id.tvEmptyState)
        val btnAddProduct = findViewById<MaterialButton>(R.id.btnAddProduct)
        val btnFinishList = findViewById<MaterialButton>(R.id.btnFinishList)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
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

        // Obtener datos del Intent
        listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString()
        listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista"
        val productList = intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product> ?: arrayListOf()
        products.addAll(productList)

        tvListName.text = listName

        // Configurar RecyclerView
        adapter = ProductAdapter(
            products,
            onEditClick = { product ->
                val intent = Intent(this, EditProductActivity::class.java)
                intent.putExtra("LIST_ID", listId)
                intent.putExtra("LIST_NAME", listName)
                intent.putExtra("PRODUCT_ID", product.id)
                intent.putExtra("PRODUCTS", ArrayList(products))
                startActivity(intent)
                finish()
            },
            onDeleteClick = { product ->
                adapter.removeProduct(product)
                updateUI(tvEmptyState)
            },
            onCheckChange = { _, _ ->
                updateUI(tvEmptyState)
            }
        )
        rvProducts.layoutManager = LinearLayoutManager(this)
        rvProducts.adapter = adapter

        updateUI(tvEmptyState)

        btnBack.setOnClickListener {
            finish()
        }

        btnAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            intent.putExtra("LIST_ID", listId)
            intent.putExtra("LIST_NAME", listName)
            intent.putExtra("PRODUCTS", ArrayList(products))
            startActivity(intent)
        }

        btnFinishList.setOnClickListener {
            ShoppingListStorage.saveOrUpdateList(
                context = this,
                list = ShoppingList(
                    id = listId,
                    name = listName,
                    products = ArrayList(products)
                )
            )
            Toast.makeText(this, "Lista '$listName' completada", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Creando nueva lista...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(tvEmptyState: TextView) {
        if (products.isEmpty()) {
            tvEmptyState.visibility = android.view.View.VISIBLE
            tvTotalPrice.text = "Total: $ 0"
        } else {
            tvEmptyState.visibility = android.view.View.GONE
            val total = products.sumOf { it.price }
            tvTotalPrice.text = "Total: $ $total"
        }
    }
}
