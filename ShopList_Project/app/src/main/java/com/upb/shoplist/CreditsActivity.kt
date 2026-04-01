package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

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

        bottomNav.selectedItemId = R.id.item_history
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
}
