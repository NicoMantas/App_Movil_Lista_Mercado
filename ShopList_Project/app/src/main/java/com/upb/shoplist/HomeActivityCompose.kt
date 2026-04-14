package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upb.shoplist.ui.theme.ShopListTheme

class HomeActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    HomeScreen(userName = intent.getStringExtra("USER_NAME") ?: "User")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(userName: String) {
    val context = LocalContext.current
    val savedLists by produceState<List<ShoppingList>>(initialValue = emptyList()) {
        value = ShoppingListStorage.getLists(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp), // Altura para permitir el desbordamiento del botón
                contentAlignment = Alignment.BottomCenter
            ) {
                // 1. El "Hueco" o muesca circular (el borde blanco que rodea al botón)
                Surface(
                    modifier = Modifier
                        .size(90.dp)
                        .offset(y = (-5).dp),
                    shape = RoundedCornerShape(100),
                    color = Color.White
                ) {}

                // 2. Barra de navegación con esquinas superiores redondeadas y color #181202
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    // Solo redondeamos las esquinas superiores (TopStart y TopEnd)
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = Color(0xFF181202) // Color solicitado
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Grupo Izquierdo
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_home),
                                contentDescription = "Inicio",
                                tint = Color(0xFFFFC123),
                                modifier = Modifier.size(28.dp).clickable { /* Home */ }
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_credits),
                                contentDescription = "Creditos",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable {
                                    context.startActivity(Intent(context, CreditsActivityCompose::class.java))
                                }
                            )
                        }

                        // Espacio central para que el icono no choque con la curva
                        Spacer(modifier = Modifier.width(90.dp))

                        // Grupo Derecho
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_history),
                                contentDescription = "Historial",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable {
                                    context.startActivity(Intent(context, CreditsActivityCompose::class.java))
                                }
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_profile),
                                contentDescription = "Perfil",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable { /* Profile */ }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(Intent(context, CreateListActivityCompose::class.java))
                },
                containerColor = Color(0xFFFFC123),
                contentColor = Color.White,
                shape = RoundedCornerShape(100),
                modifier = Modifier
                    .size(64.dp) // Tamaño del botón circular
                    .offset(y = 50.dp), // Lo baja para que calce en el hueco
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    // Aquí usamos el icono del centro (+)
                    painter = painterResource(id = R.drawable.icon_create_list),
                    contentDescription = "Crear",
                    modifier = Modifier.size(32.dp) // Icono del centro ligeramente más grande
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = Color.White
    ) { paddingValues ->
        // ... El resto del código del header y listas se mantiene igual
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()) // Importante para no tapar contenido
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_header_2),
                    contentDescription = "Header",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, top = 45.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_page),
                        contentDescription = "Logo",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(75.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Bienvenido, $userName!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.label_your_lists),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            if (savedLists.isEmpty()) {
                EmptyStateContent()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(savedLists) { list ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val intent = Intent(context, ListDetailsActivityCompose::class.java)
                                    intent.putExtra("LIST_ID", list.id)
                                    intent.putExtra("LIST_NAME", list.name)
                                    intent.putExtra("PRODUCTS", ArrayList(list.products))
                                    context.startActivity(intent)
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = list.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${list.products.size} productos - Total: $ ${list.products.sumOf { it.price }}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_backgroundhome_empty),
            contentDescription = "Sin listas",
            modifier = Modifier.size(260.dp)
        )
        Text(
            text = stringResource(R.string.empty_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.empty_description),
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShopListTheme {
        HomeScreen(userName = "Preview")
    }
}