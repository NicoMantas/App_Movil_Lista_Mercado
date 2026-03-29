package com.upb.shoplist

import android.content.Intent
import android.graphics.Color
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
import org.json.JSONArray
import java.io.InputStream
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Inicialización de Vistas (IDs vinculados a tu XML)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToLogin)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)

        // 2. Configurar Estilos de los Textos (Subrayado y Color)
        setupFooterTexts(tvGoToRegister, tvForgotPassword)

        // 3. Botón de Regresar
        btnBack.setOnClickListener {
            finish()
        }

        // 4. Ir a la pantalla de Registro
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 5. Lógica de Inicio de Sesión
        btnLogin.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passInput = etPassword.text.toString().trim()

            // Validación de campos vacíos
            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de formato de Correo (Debe tener @ y dominio)
            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                etEmail.error = "Ingresa un correo válido (ejemplo@mail.com)"
                return@setOnClickListener
            }

            // Validación contra el JSON en Assets
            val userName = checkCredentialsInJson(emailInput, passInput)

            if (userName != null) {
                // ÉXITO: Usuario encontrado
                Toast.makeText(this, "¡Bienvenido de nuevo, $userName!", Toast.LENGTH_LONG).show()

                // Aquí podrías navegar al MainActivity
                // val intent = Intent(this, MainActivity::class.java)
                // startActivity(intent)
            } else {
                // ERROR: No coinciden los datos
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Lee el archivo users.json y verifica si las credenciales coinciden.
     * Retorna el nombre del usuario si es exitoso, o null si falla.
     */
    private fun checkCredentialsInJson(emailInput: String, passInput: String): String? {
        return try {
            // IMPORTANTE: Incluir "data/" si esa es tu subcarpeta en assets
            val inputStream: InputStream = assets.open("data/users.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val userObj = jsonArray.getJSONObject(i)
                val emailJson = userObj.getString("email")
                val passJson = userObj.getString("password")

                if (emailJson.equals(emailInput.trim(), ignoreCase = true) &&
                    passJson == passInput.trim()) {
                    return userObj.getString("nombre")
                }
            }
            null
        } catch (e: Exception) {
            android.util.Log.e("LOGIN_ERROR", "No se encontró el archivo en assets/data/users.json")
            null
        }
    }

    /**
     * Aplica el formato de subrayado y color a los textos del footer.
     */
    private fun setupFooterTexts(tvReg: TextView, tvForgot: TextView) {
        // Texto de Registro
        val textReg = getString(R.string.footer_login) // Asegúrate que en strings.xml tenga <u>
        val styledReg = getHtmlText(textReg)
        val spanReg = SpannableString(styledReg)

        // Pintamos de amarillo la parte de "Registrate Aqui!"
        val wordToColor = "Registrate Aqui!"
        val start = styledReg.indexOf(wordToColor)
        if (start != -1) {
            spanReg.setSpan(
                ForegroundColorSpan(getColor(R.color.yellow_main)),
                start, start + wordToColor.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        tvReg.text = spanReg

        // Texto de Olvidaste Contraseña
        tvForgot.text = getHtmlText(getString(R.string.footer_password_login))
    }

    private fun getHtmlText(html: String): CharSequence {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(html)
        }
    }
}