package com.upb.shoplist

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import java.io.InputStream

class RecoverPasswordActivity : AppCompatActivity() {

    // Variables para las vistas
    private lateinit var layoutStepEmail: LinearLayout
    private lateinit var layoutStepPassword: LinearLayout

    private lateinit var etEmail: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText

    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoverpassword)

        // 1. Inicializar Vistas
        initViews()

        // 2. Botón Volver (Paso 1)
        findViewById<AppCompatButton>(R.id.btnBack).setOnClickListener {
            finish() // Regresa al Login
        }

        // 3. Botón Continuar (Paso 1 -> Paso 2)
        findViewById<AppCompatButton>(R.id.btnNext).setOnClickListener {
            handleNextStep()
        }

        // 4. Botón Confirmar (Finalizar proceso)
        findViewById<AppCompatButton>(R.id.btnConfirm).setOnClickListener {
            handleFinalConfirmation()
        }
    }

    private fun initViews() {
        layoutStepEmail = findViewById(R.id.layoutStepEmail)
        layoutStepPassword = findViewById(R.id.layoutStepPassword)

        etEmail = findViewById(R.id.etEmail)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword)

        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
    }

    private fun handleNextStep() {
        val email = etEmail.text.toString().trim()

        // Validaciones de correo
        if (email.isEmpty()) {
            etEmail.error = "El correo es obligatorio"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Formato inválido. Ejemplo: usuario@mail.com"
            return
        }

        // Verificar si existe en el JSON
        if (emailExistsInJson(email)) {
            // Transición suave entre vistas
            layoutStepEmail.visibility = View.GONE
            layoutStepPassword.visibility = View.VISIBLE
        } else {
            etEmail.error = "Este correo no está registrado"
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleFinalConfirmation() {
        val newPass = etNewPassword.text.toString()
        val confirmPass = etConfirmNewPassword.text.toString()

        // Limpiar errores previos
        tilPassword.error = null
        tilConfirmPassword.error = null

        when {
            newPass.isEmpty() -> tilPassword.error = "Campo obligatorio"
            newPass.length < 6 -> tilPassword.error = "Mínimo 6 caracteres"
            newPass != confirmPass -> tilConfirmPassword.error = "Las contraseñas no coinciden"
            else -> {
                // ÉXITO
                Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_LONG).show()
                finish() // Cerramos y volvemos al Login
            }
        }
    }

    /**
     * Busca el correo en el archivo JSON de assets
     */
    private fun emailExistsInJson(emailInput: String): Boolean {
        return try {
            val inputStream: InputStream = assets.open("data/users.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val userObj = jsonArray.getJSONObject(i)
                val emailJson = userObj.getString("email")
                if (emailJson.equals(emailInput, ignoreCase = true)) {
                    return true
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}