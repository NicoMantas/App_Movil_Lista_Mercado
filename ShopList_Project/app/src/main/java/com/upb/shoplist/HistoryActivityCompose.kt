package com.upb.shoplist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


class HistoryActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sessionManager = SessionManager(this)
        val userName = sessionManager.getUserName()

        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    HistoryScreen(userName = userName)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(userName: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var savedLists by remember { mutableStateOf<List<ShoppingList>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        savedLists = ShoppingListStorage.getLists(context)
        isLoading = false
    }

    val completedLists = remember(savedLists) {
        savedLists.filter { list ->
            list.products.isNotEmpty() && list.products.all { it.isPurchased }
        }
    }

    fun restoreList(listId: String) {
        scope.launch {
            val currentLists = savedLists.toMutableList()
            val index = currentLists.indexOfFirst { it.id == listId }
            if (index >= 0) {
                val restoredProducts = currentLists[index].products.map { product ->
                    product.copy(isPurchased = false)
                }
                val restoredList = currentLists[index].copy(products = restoredProducts)
                currentLists[index] = restoredList
                ShoppingListStorage.saveOrUpdateList(context, restoredList)
                savedLists = ShoppingListStorage.getLists(context)
            }
        }
    }

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
                                    scope.launch {
                                        savedLists = ShoppingListStorage.getLists(context)
                                    }
                                }
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.icon_profile),
                                contentDescription = "Perfil",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable {
                                    val intent = Intent(context, ProfileActivityCompose::class.java)
                                    context.startActivity(intent)
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
            // Header
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


            // Título de Historial
            Column(
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Historial de listas completadas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Aquí encontrarás todas las listas que ya finalizaste",
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFFFC123))
                    }
                }
                completedLists.isEmpty() -> {
                    EmptyHistoryContent()
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(completedLists) { list ->
                            val totalPrice = list.products.sumOf { it.price }
                            val totalCount = list.products.size
                            val mainCategory = list.products
                                .groupBy { it.category }
                                .maxByOrNull { it.value.size }
                                ?.key ?: "General"

                            HistoryListCard(
                                listName = list.name,
                                category = mainCategory,
                                itemCount = totalCount,
                                totalPrice = totalPrice,
                                listId = list.id,
                                createdAt = list.createdAt,
                                onRestore = { restoredListId ->
                                    restoreList(restoredListId)
                                },
                                onClick = {
                                    val intent = Intent(context, ListDetailHistoryActivityCompose::class.java)
                                    intent.putExtra("LIST_ID", list.id)
                                    intent.putExtra("LIST_NAME", list.name)
                                    intent.putExtra("PRODUCTS", ArrayList(list.products))
                                    intent.putExtra("CREATED_AT", list.createdAt)
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryListCard(
    listName: String,
    category: String,
    itemCount: Int,
    totalPrice: Double,
    listId: String,
    createdAt: Long = System.currentTimeMillis(),
    onRestore: (String) -> Unit,
    onClick: () -> Unit
) {
    var showRestoreDialog by remember { mutableStateOf(false) }

    // lógica de diálogo original
    if (showRestoreDialog) {
        AlertDialog(
            onDismissRequest = { showRestoreDialog = false },
            title = { Text("Restaurar lista") },
            text = { Text("¿Deseas volver a usar esta lista? Los productos se marcarán como pendientes.") },
            confirmButton = {
                TextButton(onClick = {
                    onRestore(listId)
                    showRestoreDialog = false
                }) {
                    Text("Restaurar", color = Color(0xFF4CAF50))
                }
            },
            dismissButton = {
                TextButton(onClick = { showRestoreDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        )
    }

    // Nueva UI mejorada
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón de Restaurar a la izquierda
            IconButton(
                onClick = { showRestoreDialog = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_restore),
                    contentDescription = "Restaurar",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información Central
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Fecha o categoría secundaria
                Text(
                    text = "Finalizada",
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fila de detalles con fondo suave (Tags)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Tag de Categoría
                    DetalleTag(painterResource(id = R.drawable.icon_kitchen), category)
                    // Tag de Cantidad
                    DetalleTag(painterResource(id = R.drawable.icon_products), "$itemCount prod.")
                }
            }

            // Precio destacado a la derecha
            if (totalPrice > 0) {
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFFC123) // Tu color amarillo/mostaza
                )
            }
        }
    }
}

// Función auxiliar para los Tags
@Composable
fun DetalleTag(painter: androidx.compose.ui.graphics.painter.Painter, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFF5F5F5), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = Color(0xFFFFC123)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = texto,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}




@Composable
fun EmptyHistoryContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_backgroundhome_empty),
            contentDescription = "Historial vacío",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Aún no hay listas completadas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cuando finalices una lista de compras,\naparecerá aquí",
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    ShopListTheme {
        HistoryScreen(userName = "Preview")
    }
}

@Composable //este es para que los demass llamen a este header
fun MainHeader(titulo: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_header_2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier.size(75.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = titulo, // Aquí pasamos el nombre de la vista
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        }
    }
}
