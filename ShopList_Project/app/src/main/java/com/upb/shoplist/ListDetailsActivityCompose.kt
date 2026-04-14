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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
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
import java.util.UUID

class ListDetailsActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ListDetailsScreen(
                        listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString(),
                        listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista",
                        products = (intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product>) ?: arrayListOf()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailsScreen(
    listId: String,
    listName: String,
    products: ArrayList<Product>
) {
    val context = LocalContext.current
    var productList by remember { mutableStateOf(products) }
    var showSnackbar by remember { mutableStateOf(false) }

    val purchasedCount = productList.count { it.isPurchased }
    val totalCount = productList.size
    val progressPercent = if (totalCount > 0) (purchasedCount.toFloat() / totalCount) * 100 else 0f
    val isAllCompleted = totalCount > 0 && purchasedCount == totalCount

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = remember { SnackbarHostState() }
            ) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = null,
                    containerColor = Color(0xFF333333),
                    contentColor = Color.White
                ) {
                    Text("No se ha completado la lista. Marca todos los productos como comprados.")
                }
            }
        },
        bottomBar = {
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
                                    context.startActivity(Intent(context, HomeActivityCompose::class.java))
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
                onClick = { /* FAB central */ },
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
            // Header con imagen de fondo y título de la app
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

                // Logo y título de la app
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

            // Botón de retroceso debajo del header
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
                    contentDescription = "Back",
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

                // Progreso de la lista
                if (totalCount > 0) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$purchasedCount/$totalCount Completado",
                            fontSize = 14.sp,
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
                    }
                } else {
                    Text(
                        text = "Sin productos aún",
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                }
            }

            // Lista de productos
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
                            modifier = Modifier.size(200.dp)
                        )
                        Text(
                            text = "No hay productos en esta lista",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Presiona 'Agregar Producto' para comenzar",
                            fontSize = 14.sp,
                            color = Color.Gray.copy(alpha = 0.7f),
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
                        ModernProductCard(
                            product = product,
                            onEditClick = {
                                val intent = Intent(context, EditProductActivityCompose::class.java)
                                intent.putExtra("LIST_ID", listId)
                                intent.putExtra("LIST_NAME", listName)
                                intent.putExtra("PRODUCT_ID", product.id)
                                intent.putExtra("PRODUCTS", ArrayList(productList))
                                context.startActivity(intent)
                            },
                            onDeleteClick = {
                                productList = ArrayList(productList.filter { it.id != product.id })
                            },
                            onCheckChange = { isChecked ->
                                val index = productList.indexOfFirst { it.id == product.id }
                                if (index >= 0) {
                                    val updated = productList.toMutableList()
                                    updated[index] = product.copy(isPurchased = isChecked)
                                    productList = ArrayList(updated)
                                }
                            }
                        )
                    }
                }
            }

            // Botones de acción
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        val intent = Intent(context, AddProductActivityCompose::class.java)
                        intent.putExtra("LIST_ID", listId)
                        intent.putExtra("LIST_NAME", listName)
                        intent.putExtra("PRODUCTS", ArrayList(productList))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC123),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Agregar Producto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Botón Finalizar Lista con validación
                Button(
                    onClick = {
                        if (isAllCompleted) {
                            val updatedList = ShoppingList(
                                id = listId,
                                name = listName,
                                products = productList
                            )
                            ShoppingListStorage.saveOrUpdateList(context, updatedList)
                            (context as? ComponentActivity)?.finish()
                        } else {
                            showSnackbar = true
                            // Mostrar un mensaje visual
                            android.widget.Toast.makeText(
                                context,
                                "No se ha completado la lista. Marca todos los productos como comprados.",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAllCompleted) Color(0xFFFFC123) else Color(0xFFCCCCCC),
                        contentColor = if (isAllCompleted) Color.Black else Color.Gray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    enabled = isAllCompleted
                ) {
                    Text(
                        text = "Finalizar Lista",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ModernProductCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckChange: (Boolean) -> Unit
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
            Checkbox(
                checked = product.isPurchased,
                onCheckedChange = { onCheckChange(it) },
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
                    Text(
                        text = product.category,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Cantidad: ${product.quantity}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "$${String.format("%.2f", product.price)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFC123)
                    )
                }
            }

            Row {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFFFFC123)
                    )
                }
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDetailsScreenPreview() {
    ShopListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ListDetailsScreen(
                listId = "preview_id",
                listName = "Grocery Shopping List 1",
                products = arrayListOf(
                    Product("1", "Leche", "Lacteos", 2, 3.50, false),
                    Product("2", "Huevos", "Alimentos", 12, 5.00, false),
                    Product("3", "Mantequilla", "Lacteos", 1, 2.50, true),
                    Product("4", "Queso", "Lacteos", 1, 4.00, false)
                )
            )
        }
    }
}