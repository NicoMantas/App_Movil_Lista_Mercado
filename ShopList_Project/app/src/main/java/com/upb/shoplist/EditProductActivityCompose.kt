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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.shape.RoundedCornerShape
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
                    // Error: producto no encontrado
                    Text("Producto no encontrado")
                }
            }
        }
    }
}

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

    val categories = listOf("General", "Alimentos", "Limpieza", "Electrónica", "Ropa", "Hogar")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            Image(
                painter = painterResource(R.drawable.background_header_2),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.padding(start = 25.dp, top = 45.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(R.drawable.icon_page), null, Modifier.size(60.dp))
                Spacer(Modifier.width(75.dp))
                Text(stringResource(R.string.app_name), fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Botón back
        IconButton(
            onClick = {
                val intent = Intent(context, ListDetailsActivityCompose::class.java)
                intent.putExtra("LIST_ID", listId)
                intent.putExtra("LIST_NAME", listName)
                intent.putExtra("PRODUCTS", products)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.padding(start = 25.dp, top = 10.dp).size(45.dp)
        ) {
            Icon(Icons.Default.ArrowBack, null, tint = Color.White)
        }

        // Formulario
        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.title_edit_product),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(Modifier.height(36.dp))

            // Nombre del producto
            Text(
                text = stringResource(R.string.label_product_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            OutlinedTextField(
                value = productName,
                onValueChange = {
                    productName = it
                    nameError = null
                },
                isError = nameError != null,
                supportingText = { nameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(24.dp))

            // Categoría
            Text(
                text = stringResource(R.string.label_product_category),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Simplified dropdown
            var expanded by remember { mutableStateOf(false) }
            Box {
                TextField(
                    value = categories[selectedCategory],
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Text("▼")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFC123),
                        unfocusedBorderColor = Color(0xFFFFE0A3)
                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEachIndexed { index, category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = index
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Cantidad
            Text(
                text = stringResource(R.string.label_product_quantity),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                    quantityError = null
                },
                isError = quantityError != null,
                supportingText = { quantityError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(24.dp))

            // Precio
            Text(
                text = stringResource(R.string.label_product_price),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(60.dp))

            // Botón guardar
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
                modifier = Modifier.fillMaxWidth().height(74.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC123), contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.button_save_changes), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun getCategoryIndex(category: String): Int {
    val categories = listOf("General", "Alimentos", "Limpieza", "Electrónica", "Ropa", "Hogar")
    return categories.indexOf(category).takeIf { it >= 0 } ?: 0
}