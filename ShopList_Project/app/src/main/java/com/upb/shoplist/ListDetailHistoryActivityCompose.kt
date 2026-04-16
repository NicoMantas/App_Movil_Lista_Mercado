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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import java.text.SimpleDateFormat
import java.util.*

class ListDetailHistoryActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ListDetailHistoryScreen(
                        listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString(),
                        listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista",
                        products = (intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product>) ?: arrayListOf(),
                        createdAt = intent.getLongExtra("CREATED_AT", System.currentTimeMillis())
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailHistoryScreen(
    listId: String,
    listName: String,
    products: ArrayList<Product>,
    createdAt: Long = System.currentTimeMillis()
) {
    val context = LocalContext.current
    val productList by remember { mutableStateOf(products) }

    val purchasedCount = productList.count { it.isPurchased }
    val totalCount = productList.size
    val progressPercent = if (totalCount > 0) (purchasedCount.toFloat() / totalCount) * 100 else 0f
    val totalPrice = productList.sumOf { it.price }

    // Formatear fecha
    val dateFormat = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
    val formattedDate = dateFormat.format(createdAt)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Barra de navegación inferior (mismo estilo que HistoryScreen)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    modifier = Modifier
                        .size(90.dp)
                        .offset(y = (-5).dp),
                    shape = RoundedCornerShape(100),
                    color = Color.White
                ) {}

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = Color(0xFF181202)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_home),
                                contentDescription = "Inicio",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable {
                                    val intent = Intent(context, HomeActivityCompose::class.java)
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish()
                                }
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

                        Spacer(modifier = Modifier.width(90.dp))

                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_history),
                                contentDescription = "Historial",
                                tint = Color(0xFFFFC123),
                                modifier = Modifier.size(28.dp).clickable {
                                    val intent = Intent(context, HistoryActivityCompose::class.java)
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish()
                                }
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_profile),
                                contentDescription = "Perfil",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable {
                                    // TODO: Implementar pantalla de perfil
                                }
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
                    .size(64.dp)
                    .offset(y = 50.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_create_list),
                    contentDescription = "Crear",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            // Header con imagen de fondo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
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
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(75.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // Botón de volver (debajo del header)
            IconButton(
                onClick = { (context as? ComponentActivity)?.finish() },
                modifier = Modifier
                    .padding(start = 25.dp, top = 10.dp)
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFC123),
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Información de la lista
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 16.dp)
            ) {
                Text(
                    text = listName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha de creación
                Text(
                    text = "Creada el: $formattedDate",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Resumen de la lista
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Productos
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = totalCount.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFC123)
                            )
                            Text(
                                text = "Productos",
                                fontSize = 12.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }

                        // Completados
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$purchasedCount/$totalCount",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Text(
                                text = "Completados",
                                fontSize = 12.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }

                        // Total
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$${String.format("%.2f", totalPrice)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFC123)
                            )
                            Text(
                                text = "Total Estimado",
                                fontSize = 12.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                // Barra de progreso
                if (totalCount > 0) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progreso:",
                            fontSize = 12.sp,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        LinearProgressIndicator(
                            progress = { progressPercent / 100f },
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFFFFC123),
                            trackColor = Color(0xFFFFE0A3)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${progressPercent.toInt()}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFC123)
                        )
                    }
                }
            }

            // Lista de productos (SOLO VISUALIZACIÓN)
            if (productList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.image_backgroundhome_empty),
                            contentDescription = "Sin productos",
                            modifier = Modifier.size(180.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay productos en esta lista",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productList) { product ->
                        HistoryProductCard(product = product)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryProductCard(
    product: Product
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (product.isPurchased) Color(0xFFF5F5F5) else Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox (solo visual, deshabilitado)
            Checkbox(
                checked = product.isPurchased,
                onCheckedChange = null,
                enabled = false,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFFFC123),
                    uncheckedColor = Color(0xFFFFE0A3)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (product.isPurchased) Color.Gray else Color.Black
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Categoría
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_kitchen),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = Color(0xFFFFC123)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = product.category,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    // Cantidad
                    Text(
                        text = "Cant: ${product.quantity}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    // Precio
                    if (product.price > 0) {
                        Text(
                            text = "$${String.format("%.2f", product.price)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFC123)
                        )
                    }
                }
            }

            // Icono de estado
            if (product.isPurchased) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_check),
                    contentDescription = "Comprado",
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDetailHistoryScreenPreview() {
    ShopListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ListDetailHistoryScreen(
                listId = "preview_id",
                listName = "Lista de Mercado - Marzo 2024",
                products = arrayListOf(
                    Product("1", "Leche", "Lácteos", 2, 3.50, true),
                    Product("2", "Huevos", "Alimentos", 12, 5.00, true),
                    Product("3", "Mantequilla", "Lácteos", 1, 2.50, true),
                    Product("4", "Queso", "Lácteos", 1, 4.00, false),
                    Product("5", "Pan", "Panadería", 2, 2.00, true)
                ),
                createdAt = System.currentTimeMillis()
            )
        }
    }
}