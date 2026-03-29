package com.upb.shoplist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToLogin)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)

        // Configurar estilos (Subrayados y Colores)
        setupFooterTexts(tvGoToRegister, tvForgotPassword)

        btnBack.setOnClickListener { finish() }

        // Navegación al Registro
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Olvidar contraseña
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Redirigiendo a recuperación...", Toast.LENGTH_SHORT).show()
        }

        btnLogin.setOnClickListener {
            if (etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()) {
                Toast.makeText(this, "Bienvenido a ShopList", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Completa los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFooterTexts(tvReg: TextView, tvForgot: TextView) {
        // Estilo para "Regístrate Aquí"
        val rawReg = getString(R.string.footer_register)
        val styledReg = getHtml(rawReg)
        val spanReg = SpannableString(styledReg)
        val word = "Registrate Aqui!"

        val start = styledReg.indexOf(word)
        if (start != -1) {
            spanReg.setSpan(
                ForegroundColorSpan(getColor(R.color.yellow_main)),
                start, start + word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        tvReg.text = spanReg

        // Estilo para "Olvidaste contraseña" (solo subrayado)
        tvForgot.text = getHtml(getString(R.string.footer_password_login))
    }

    private fun getHtml(text: String): CharSequence {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(text)
        }
    }
}