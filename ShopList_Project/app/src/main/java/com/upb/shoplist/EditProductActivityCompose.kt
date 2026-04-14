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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import java.util.UUID

class EditProductActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString()
                    val listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista"
                    val productId = intent.getStringExtra("PRODUCT_ID")
                    val products = (intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product>) ?: arrayListOf()
                    val product = products.find { it.id == productId }

                    if (product != null) {
                        EditProductScreen(
                            listId = listId,
                            listName = listName,
                            product = product,
                            products = products
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Producto no encontrado",
                                fontSize = 18.sp,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    listId: String,
    listName: String,
    product: Product,
    products: ArrayList<Product>
) {
    val context = LocalContext.current
    var productName by remember { mutableStateOf(product.name) }
    var selectedCategory by remember { mutableStateOf(getCategoryIndex(product.category)) }
    var quantity by remember { mutableStateOf(product.quantity.toString()) }
    var price by remember { mutableStateOf(product.price.toString()) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("General", "Alimentos", "Limpieza", "Electrónica", "Ropa", "Hogar")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                .verticalScroll(rememberScrollState())
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

            // Botón de retroceso debajo del header (estilo consistente)
            IconButton(
                onClick = {
                    val intent = Intent(context, ListDetailsActivityCompose::class.java)
                    intent.putExtra("LIST_ID", listId)
                    intent.putExtra("LIST_NAME", listName)
                    intent.putExtra("PRODUCTS", products)
                    context.startActivity(intent)
                    (context as? ComponentActivity)?.finish()
                },
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

            // Formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título principal
                Text(
                    text = stringResource(R.string.title_edit_product),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Editando en: $listName",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Nombre del producto
                Text(
                    text = stringResource(R.string.label_product_name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = productName,
                    onValueChange = {
                        productName = it
                        nameError = null
                    },
                    isError = nameError != null,
                    supportingText = {
                        if (nameError != null) {
                            Text(
                                text = nameError!!,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Nombre del producto",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFC123),
                        unfocusedBorderColor = Color(0xFFFFE0A3),
                        focusedLabelColor = Color(0xFFFFC123),
                        unfocusedLabelColor = Color.Gray
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Categoría
                Text(
                    text = stringResource(R.string.label_product_category),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                // Dropdown para categorías
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = categories[selectedCategory],
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Text("▼", fontSize = 20.sp)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFFC123),
                            unfocusedBorderColor = Color(0xFFFFE0A3)
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEachIndexed { index, category ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = category,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                },
                                onClick = {
                                    selectedCategory = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cantidad
                Text(
                    text = stringResource(R.string.label_product_quantity),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = {
                        quantity = it
                        quantityError = null
                    },
                    isError = quantityError != null,
                    supportingText = {
                        if (quantityError != null) {
                            Text(
                                text = quantityError!!,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Cantidad",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFC123),
                        unfocusedBorderColor = Color(0xFFFFE0A3)
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Precio
                Text(
                    text = stringResource(R.string.label_product_price),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Precio",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFC123),
                        unfocusedBorderColor = Color(0xFFFFE0A3)
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Botón guardar cambios
                Button(
                    onClick = {
                        when {
                            productName.isBlank() -> nameError = "Ingresa el nombre del producto"
                            quantity.isBlank() -> quantityError = "Ingresa la cantidad"
                            else -> {
                                val updatedProducts = ArrayList(products)
                                val index = updatedProducts.indexOfFirst { it.id == product.id }
                                if (index >= 0) {
                                    updatedProducts[index] = product.copy(
                                        name = productName,
                                        category = categories[selectedCategory],
                                        quantity = quantity.toIntOrNull() ?: 1,
                                        price = price.toDoubleOrNull() ?: 0.0
                                    )
                                }

                                val intent = Intent(context, ListDetailsActivityCompose::class.java)
                                intent.putExtra("LIST_ID", listId)
                                intent.putExtra("LIST_NAME", listName)
                                intent.putExtra("PRODUCTS", updatedProducts)
                                context.startActivity(intent)
                                (context as? ComponentActivity)?.finish()
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
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.button_save_changes),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun getCategoryIndex(category: String): Int {
    val categories = listOf("General", "Alimentos", "Limpieza", "Electrónica", "Ropa", "Hogar")
    return categories.indexOf(category).takeIf { it >= 0 } ?: 0
}

@Preview(showBackground = true)
@Composable
fun EditProductScreenPreview() {
    ShopListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            val sampleProduct = Product(
                id = "1",
                name = "Leche",
                category = "Lacteos",
                quantity = 2,
                price = 3.50,
                isPurchased = false
            )
            EditProductScreen(
                listId = "preview_id",
                listName = "Lista de Prueba",
                product = sampleProduct,
                products = arrayListOf(sampleProduct)
            )
        }
    }
}