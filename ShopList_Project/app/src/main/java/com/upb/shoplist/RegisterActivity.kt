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

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnFinalizeRegister = findViewById<AppCompatButton>(R.id.btnFinalizeRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        setupFooterText(tvGoToLogin)

        // Volver atrás
        btnBack.setOnClickListener { finish() }

        // Navegación al Login
        tvGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Lógica de Registro
        btnFinalizeRegister.setOnClickListener {
            val pass = etPassword.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            when {
                pass.isEmpty() -> etPassword.error = "Campo obligatorio"
                pass.length < 6 -> {
                    etPassword.error = "Mínimo 6 caracteres"
                    Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show()
                }
                !pass.any { it.isDigit() } -> etPassword.error = "Debe incluir un número"
                pass != confirmPass -> etConfirmPassword.error = "Las contraseñas no coinciden"
                else -> Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupFooterText(textView: TextView) {
        val fullTextRaw = getString(R.string.footer_login)
        val styledText = getHtmlStyledText(fullTextRaw)
        val spannable = SpannableString(styledText)
        val wordToColor = "Inicia Sesión Aqui!" // Asegúrate que coincida con strings.xml

        val start = styledText.indexOf(wordToColor)
        if (start != -1) {
            spannable.setSpan(
                ForegroundColorSpan(getColor(R.color.yellow_main)),
                start, start + wordToColor.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = spannable
    }

    private fun getHtmlStyledText(rawString: String): CharSequence {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(rawString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(rawString)
        }
    }
}