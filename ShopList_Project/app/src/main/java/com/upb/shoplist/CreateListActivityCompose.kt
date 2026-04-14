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

class CreateListActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    CreateListScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListScreen() {
    val context = LocalContext.current
    var listName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

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
                                painter = painterResource(id = R.drawable.icon_search),
                                contentDescription = "Buscar",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp).clickable { /* Search */ }
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

            // Botón de retroceso
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
                    text = stringResource(R.string.title_create_list),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Descripción
                Text(
                    text = stringResource(R.string.create_list_description),
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Label del campo
                Text(
                    text = stringResource(R.string.label_list_name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                // Campo de texto
                OutlinedTextField(
                    value = listName,
                    onValueChange = {
                        listName = it
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
                            text = stringResource(R.string.hint_list_name),
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

                Spacer(modifier = Modifier.height(24.dp))

                // Sección "Agregar producto" (Opcional)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Agrega tu primer producto: (Opcional)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Agrega Aquí -->",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )

                            FloatingActionButton(
                                onClick = {
                                    if (listName.isBlank()) {
                                        nameError = "Primero ingresa un nombre para la lista"
                                    } else {
                                        val intent = Intent(context, AddProductActivityCompose::class.java)
                                        intent.putExtra("LIST_ID", UUID.randomUUID().toString())
                                        intent.putExtra("LIST_NAME", listName)
                                        intent.putExtra("PRODUCTS", arrayListOf<Product>())
                                        intent.putExtra("IS_CREATING_LIST", true)
                                        context.startActivity(intent)
                                        (context as? ComponentActivity)?.finish()
                                    }
                                },
                                containerColor = Color(0xFFFFC123),
                                contentColor = Color.Black,
                                modifier = Modifier.size(48.dp),
                                shape = RoundedCornerShape(20.dp),
                                elevation = FloatingActionButtonDefaults.elevation(0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_create_list),
                                    contentDescription = "Agregar Producto",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Botón "Confirmar y Crear" - CREA LA LISTA DIRECTAMENTE
                Button(
                    onClick = {
                        if (listName.isBlank()) {
                            nameError = "Ingresa un nombre para la lista"
                        } else {
                            // Crear la lista vacía
                            val newList = ShoppingList(
                                id = UUID.randomUUID().toString(),
                                name = listName,
                                products = emptyList()
                            )
                            ShoppingListStorage.saveOrUpdateList(context, newList)

                            // Ir al Home
                            val intent = Intent(context, HomeActivityCompose::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            context.startActivity(intent)
                            (context as? ComponentActivity)?.finish()
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
                        text = "Confirmar y Crear",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListScreenPreview() {
    ShopListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            CreateListScreen()
        }
    }
}