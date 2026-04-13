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

class ListDetailsActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                ListDetailsScreen(
                    listId = intent.getStringExtra("LIST_ID") ?: UUID.randomUUID().toString(),
                    listName = intent.getStringExtra("LIST_NAME") ?: "Mi Lista",
                    products = (intent.getSerializableExtra("PRODUCTS") as? ArrayList<Product>) ?: arrayListOf()
                )
            }
        }
    }
}

@Composable
fun ListDetailsScreen(
    listId: String,
    listName: String,
    products: ArrayList<Product>
) {
    val context = LocalContext.current
    var productList by remember { mutableStateOf(products) }
    var showEmptyState by remember { mutableStateOf(products.isEmpty()) }

    val totalPrice = productList.sumOf { it.price }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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

        // Info de la lista
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { (context as? ComponentActivity)?.finish() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Total: $ $totalPrice",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFC123)
                )
            }
        }

        // Lista de productos
        if (showEmptyState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.text_no_products),
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productList) { product ->
                    ProductCard(
                        product = product,
                        onEditClick = {
                            val intent = Intent(context, EditProductActivityCompose::class.java)
                            intent.putExtra("LIST_ID", listId)
                            intent.putExtra("LIST_NAME", listName)
                            intent.putExtra("PRODUCT_ID", product.id)
                            intent.putExtra("PRODUCTS", ArrayList(productList))
                            context.startActivity(intent)
                            (context as? ComponentActivity)?.finish()
                        },
                        onDeleteClick = {
                            productList = ArrayList(productList.filter { it.id != product.id })
                            showEmptyState = productList.isEmpty()
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, AddProductActivityCompose::class.java)
                    intent.putExtra("LIST_ID", listId)
                    intent.putExtra("LIST_NAME", listName)
                    intent.putExtra("PRODUCTS", ArrayList(productList))
                    context.startActivity(intent)
                    (context as? ComponentActivity)?.finish()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC123), contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.button_add_more_products), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    ShoppingListStorage.saveOrUpdateList(
                        context = context,
                        list = ShoppingList(
                            id = listId,
                            name = listName,
                            products = productList
                        )
                    )
                    (context as? ComponentActivity)?.finish()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC123), contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.button_finish_list), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCheckChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = product.isPurchased,
                onCheckedChange = { onCheckChange(it) },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFFC123))
            )

            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (product.isPurchased) Color.Gray else Color.Black
                )
                Text(
                    text = product.category,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Cantidad: ${product.quantity}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "$${product.price}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFC123)
                    )
                }
            }

            Column {
                IconButton(onClick = onEditClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.icon_edit),
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = onDeleteClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.icon_arrow_left),
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}