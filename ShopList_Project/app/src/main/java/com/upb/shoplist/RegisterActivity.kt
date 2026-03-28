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

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Inicializar Vistas
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnFinalizeRegister = findViewById<AppCompatButton>(R.id.btnFinalizeRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        // Referencias a los campos (por si quieres validar datos luego)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        // 2. Configurar el texto inferior (Subrayado + Color Amarillo)
        setupFooterText(tvGoToLogin)

        // 3. Evento Botón Volver
        btnBack.setOnClickListener {
            finish() // Cierra esta actividad y vuelve al Splash
        }

        // 4. Evento Botón Registrarse
        btnFinalizeRegister.setOnClickListener {
            val nombre = etNombre.text.toString()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Bienvenido $nombre! Registro exitoso", Toast.LENGTH_SHORT).show()
                // Aquí iría la lógica para guardar en base de datos
            }
        }

        // 5. Evento Texto "Inicia Sesión Aquí"
        tvGoToLogin.setOnClickListener {
            Toast.makeText(this, "Redirigiendo al Login...", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, LoginActivity::class.java)
            // startActivity(intent)
        }
    }

    /**
     * Configura el estilo visual del footer para evitar errores de índices
     */
    private fun setupFooterText(textView: TextView) {
        val fullTextRaw = getString(R.string.footer_login) // "Ya tienes una cuenta ? <u>Inicia Sesión Aqui!</u>"

        // Convertir HTML a Spanned (procesa el subrayado <u>)
        val styledText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(fullTextRaw, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(fullTextRaw)
        }

        val spannable = SpannableString(styledText)

        // Aplicar color amarillo solo a la parte específica
        val wordToColor = "Inicia Sesión Aqui!"
        val start = styledText.indexOf(wordToColor)

        if (start != -1) {
            val end = start + wordToColor.length
            spannable.setSpan(
                ForegroundColorSpan(getColor(R.color.yellow_main)),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannable
    }
}