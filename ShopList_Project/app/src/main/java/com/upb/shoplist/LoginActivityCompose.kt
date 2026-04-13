package com.upb.shoplist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upb.shoplist.ui.theme.ShopListTheme
import org.json.JSONArray
import java.io.InputStream
import androidx.compose.ui.layout.ContentScale


class LoginActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                LoginScreen(
                    context = this,
                    onBackClick = { finish() },
                    onRegisterClick = {
                        startActivity(Intent(this, RegisterActivityCompose::class.java))
                        finish()
                    },
                    onForgotPasswordClick = {
                        startActivity(Intent(this, RecoverPasswordActivityCompose::class.java))
                    },
                    onLoginSuccess = { userName ->
                        val sharedPref = getSharedPreferences("SessionData", MODE_PRIVATE)
                        sharedPref.edit().apply {
                            putBoolean("isLoggedIn", true)
                            putString("userName", userName)
                            apply()
                        }
                        val intent = Intent(this, HomeMainActivity::class.java)
                        intent.putExtra("USER_NAME", userName)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    context: Context,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val footerText = buildAnnotatedString {
        append("¿No tienes una cuenta? ")
        withStyle(style = SpanStyle(color = Color(0xFFFFC123), fontWeight = FontWeight.Bold)) {
            append("Registrate Aqui!")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header con imagen de fondo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            // Fondo del header
            Image(
                painter = painterResource(id = R.drawable.header_background),
                contentDescription = "Header Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )

            // Contenido superpuesto
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_page),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(75.dp)
                )
                Spacer(modifier = Modifier.width(90.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Botón de retroceso
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(start = 25.dp, top = 10.dp)
                .size(45.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = { Text("Correo Electrónico") },
                placeholder = { Text("usuario@ejemplo.com") },
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campo Password
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = { Text("Contraseña") },
                placeholder = { Text("••••••") },
                isError = passwordError != null,
                supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                )
            )

            // Olvidé contraseña
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = stringResource(id = R.string.footer_password_login),
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            // Botón Login
            Button(
                onClick = {
                    when {
                        email.isEmpty() -> emailError = "El correo es obligatorio"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                            emailError = "Formato inválido. Ejemplo: usuario@mail.com"
                        password.isEmpty() -> passwordError = "La contraseña es obligatoria"
                        else -> {
                            isLoading = true
                            val userName = checkCredentialsInJson(context, email, password)
                            if (userName != null) {
                                onLoginSuccess(userName)
                            } else {
                                passwordError = "Correo o contraseña incorrectos"
                                isLoading = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC123),
                    contentColor = Color.Black
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black)
                } else {
                    Text(
                        text = stringResource(id = R.string.button_login),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer
            TextButton(onClick = onRegisterClick) {
                Text(
                    text = footerText,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

private fun checkCredentialsInJson(context: Context, emailInput: String, passInput: String): String? {
    return try {
        val inputStream: InputStream = context.assets.open("data/users.json")
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
        null
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ShopListTheme {
        LoginScreen(
            context = androidx.compose.ui.platform.LocalContext.current,
            onBackClick = {},
            onRegisterClick = {},
            onForgotPasswordClick = {},
            onLoginSuccess = {}
        )
    }
}