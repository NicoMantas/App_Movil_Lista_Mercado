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

class AddProductActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AddProductScreen(
                        listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString(),
                        listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista",
                        existingProducts = (intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product>) ?: arrayListOf()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    listId: String,
    listName: String,
    existingProducts: ArrayList<Product>
) {
    val context = LocalContext.current
    var productName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

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
                onClick = { /* FAB central - no acción específica */ },
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

            // Botón de retroceso con fondo amarillo redondeado
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
                    tint = Color.Black,
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
                    text = stringResource(R.string.title_add_product),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Agregando a: $listName",
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
                            text = stringResource(R.string.hint_product_name),
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
                            text = stringResource(R.string.hint_product_quantity),
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
                            text = stringResource(R.string.hint_product_price),
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

                // Botón agregar producto
                Button(
                    onClick = {
                        when {
                            productName.isBlank() -> nameError = "Ingresa el nombre del producto"
                            quantity.isBlank() -> quantityError = "Ingresa la cantidad"
                            else -> {
                                val newProduct = Product(
                                    id = UUID.randomUUID().toString(),
                                    name = productName,
                                    category = categories[selectedCategory],
                                    quantity = quantity.toIntOrNull() ?: 1,
                                    price = price.toDoubleOrNull() ?: 0.0,
                                    isPurchased = false
                                )
                                val updatedProducts = ArrayList(existingProducts)
                                updatedProducts.add(newProduct)

                                // Guardar la lista con el producto agregado
                                val updatedList = ShoppingList(
                                    id = listId,
                                    name = listName,
                                    products = updatedProducts
                                )
                                ShoppingListStorage.saveOrUpdateList(context, updatedList)

                                // Ir a ListDetailsActivityCompose
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
                        text = stringResource(R.string.button_add_product),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón saltar (opcional)
                TextButton(
                    onClick = {
                        val intent = Intent(context, ListDetailsActivityCompose::class.java)
                        intent.putExtra("LIST_ID", listId)
                        intent.putExtra("LIST_NAME", listName)
                        intent.putExtra("PRODUCTS", existingProducts)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Omitir y ver lista",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    ShopListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            AddProductScreen(
                listId = "preview_id",
                listName = "Lista de Prueba",
                existingProducts = arrayListOf()
            )
        }
    }
}