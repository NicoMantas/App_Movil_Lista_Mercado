package com.upb.shoplist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
// IMPORTANTE: Asegúrate de tener estos 3 imports para el JSON
import org.json.JSONArray
import java.io.InputStream
import java.nio.charset.Charset

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. Vinculación de Vistas
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnFinalizeRegister = findViewById<AppCompatButton>(R.id.btnFinalizeRegister)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        // Layouts para errores (para no tapar el icono del ojo)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)

        // Configurar texto del footer (Ya tienes cuenta?...)
        setupFooterText(tvGoToLogin)

        // Botón Regresar
        btnBack.setOnClickListener { finish() }

        // Navegación al Login
        tvGoToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Lógica de Registro
        btnFinalizeRegister.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            // Resetear errores
            etNombre.error = null
            etEmail.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null

            when {
                nombre.isEmpty() -> {
                    etNombre.error = "Ingresa tu nombre completo"
                    etNombre.requestFocus()
                }
                email.isEmpty() -> {
                    etEmail.error = "El correo es obligatorio"
                    etEmail.requestFocus()
                }
                // VALIDACIÓN 1: Formato correcto con ejemplo
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Formato inválido. Ejemplo: usuario@mail.com"
                    etEmail.requestFocus()
                }
                // VALIDACIÓN 2: Verificar si ya existe en el JSON
                emailExistsInJson(email) -> {
                    etEmail.error = "Este correo ya está registrado"
                    etEmail.requestFocus()
                    Toast.makeText(this, "El usuario ya tiene una cuenta", Toast.LENGTH_SHORT).show()
                }
                // VALIDACIÓN 3: Contraseñas
                pass.isEmpty() -> {
                    tilPassword.error = "La contraseña es obligatoria"
                }
                pass.length < 6 -> {
                    tilPassword.error = "Mínimo 6 caracteres"
                }
                !pass.any { it.isDigit() } -> {
                    tilPassword.error = "Debe incluir al menos un número"
                }
                pass != confirmPass -> {
                    tilConfirmPassword.error = "Las contraseñas no coinciden"
                }
                else -> {
                    Toast.makeText(this, "¡Registro Exitoso, $nombre!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    /**
     * Función para leer el JSON y verificar si el correo ya está en uso
     */
    private fun emailExistsInJson(emailInput: String): Boolean {
        return try {
            val inputStream: InputStream = assets.open("data/users.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val userObj = jsonArray.getJSONObject(i)
                val emailJson = userObj.getString("email")
                // Comparación ignorando mayúsculas/minúsculas
                if (emailJson.equals(emailInput.trim(), ignoreCase = true)) {
                    return true
                }
            }
            false
        } catch (e: Exception) {
            android.util.Log.e("REGISTER_ERROR", "Error leyendo JSON: ${e.message}")
            false
        }
    }

    private fun setupFooterText(textView: TextView) {
        val fullTextRaw = getString(R.string.footer_login)
        val styledText = getHtmlStyledText(fullTextRaw)
        val spannable = SpannableString(styledText)

        // Debe coincidir exacto con tu strings.xml: "Inicia Sesión Aqui!"
        val wordToColor = "Inicia Sesión Aqui!"

        val start = styledText.toString().indexOf(wordToColor)
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