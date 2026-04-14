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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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


class RegisterActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                RegisterScreen(
                    context = this,
                    onBackClick = {
                        startActivity(Intent(this, SplashActivityCompose::class.java))
                        finish()
                    },
                    onLoginClick = {
                        startActivity(Intent(this, LoginActivityCompose::class.java))
                        finish()
                    },
                    onRegisterSuccess = {
                        startActivity(Intent(this, LoginActivityCompose::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    context: Context,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val footerText = buildAnnotatedString {
        append("¿Ya tienes una cuenta? ")
        withStyle(style = SpanStyle(color = Color(0xFFFFC123), fontWeight = FontWeight.Bold)) {
            append("Inicia Sesión Aqui!")
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
                    modifier = Modifier.size(70.dp)
                )
                Spacer(modifier = Modifier.width(77.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
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
                .background(
                    brush = SolidColor(Color(0xFFFFC123)),
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_left),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
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
                text = "Crea Una Cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // Campo Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = null
                },
                label = { Text("Nombre Completo") },
                isError = nombreError != null,
                supportingText = { nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = { Text("Correo Electrónico") },
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

            Spacer(modifier = Modifier.height(10.dp))

            // Campo Confirmar Password
            var confirmPasswordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                label = { Text("Confirmar Contraseña") },
                isError = confirmPasswordError != null,
                supportingText = { confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (confirmPasswordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Botón Registrar
            Button(
                onClick = {
                    when {
                        nombre.isEmpty() -> nombreError = "Ingresa tu nombre completo"
                        email.isEmpty() -> emailError = "El correo es obligatorio"
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                            emailError = "Formato inválido. Ejemplo: usuario@mail.com"
                        emailExistsInJson(context, email) -> {
                            emailError = "Este correo ya está registrado"
                        }
                        password.isEmpty() -> passwordError = "La contraseña es obligatoria"
                        password.length < 6 -> passwordError = "Mínimo 6 caracteres"
                        !password.any { it.isDigit() } -> passwordError = "Debe incluir al menos un número"
                        password != confirmPassword -> confirmPasswordError = "Las contraseñas no coinciden"
                        else -> onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC123),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Registrate",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Footer
            TextButton(onClick = onLoginClick) {
                Text(
                    text = footerText,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

private fun emailExistsInJson(context: Context, emailInput: String): Boolean {
    return try {
        val inputStream: InputStream = context.assets.open("data/users.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val userObj = jsonArray.getJSONObject(i)
            val emailJson = userObj.getString("email")
            if (emailJson.equals(emailInput.trim(), ignoreCase = true)) {
                return true
            }
        }
        false
    } catch (e: Exception) {
        false
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ShopListTheme {
        RegisterScreen(
            context = androidx.compose.ui.platform.LocalContext.current,
            onBackClick = {},
            onLoginClick = {},
            onRegisterSuccess = {}
        )
    }
}