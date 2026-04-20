package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upb.shoplist.ui.theme.ShopListTheme


class SplashActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                // SOLO CAMBIO ESTO: llamar a AppNavigation en lugar de SplashScreen directamente
                AppNavigation()
            }
        }
    }
}

@Composable
fun SplashScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ScrollView para todo el contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header con el vector como fondo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_header_2),
                    contentDescription = "Header Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, top = 50.dp), // Ajustado para mejor centrado vertical
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo con transparencia
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground), // Usa el foreground que configuramos antes
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(75.dp) // Un poco más grande para que luzca
                            .clip(RoundedCornerShape(12.dp)) // un leve redondeado pa q se vea bien
                    )

                    Spacer(modifier = Modifier.width(15.dp)) // Menos espacio para que el texto no se pegue al borde

                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 32.sp, // Aumentado para resaltar el nombre de la app
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            }
            // Contenido inferior
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 45.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = "Shopping Cart",
                    modifier = Modifier
                        .size(260.dp)
                        .padding(top = 30.dp)
                )

                Text(
                    text = stringResource(id = R.string.description_app),
                    fontSize = 15.sp,
                    color = Color(0xFF4A4A4A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                )

                // Aumenté el padding superior de 20.dp a 40.dp para bajar los botones
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp, bottom = 30.dp), // Cambiado de 20.dp a 40.dp
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC123),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.button_register),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC123),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.button_login),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ShopListTheme {
        SplashScreen(
            onRegisterClick = {},
            onLoginClick = {}
        )
    }
}