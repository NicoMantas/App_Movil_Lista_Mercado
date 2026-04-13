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

class CreateListActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopListTheme {
                CreateListScreen()
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
            onClick = { (context as? ComponentActivity)?.finish() },
            modifier = Modifier.padding(start = 25.dp, top = 10.dp).size(45.dp)
        ) {
            Icon(Icons.Default.ArrowBack, null, tint = Color.White)
        }

        // Formulario
        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_create_list),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = stringResource(R.string.create_list_description),
                fontSize = 26.sp,
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(Modifier.height(52.dp))

            Text(
                text = stringResource(R.string.label_list_name),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = listName,
                onValueChange = {
                    listName = it
                    nameError = null
                },
                isError = nameError != null,
                supportingText = { nameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text(stringResource(R.string.hint_list_name), fontSize = 24.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFC123),
                    unfocusedBorderColor = Color(0xFFFFE0A3)
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp)
            )

            Spacer(Modifier.height(100.dp))

            Button(
                onClick = {
                    if (listName.isBlank()) {
                        nameError = "Ingresa un nombre para la lista"
                    } else {
                        val intent = Intent(context, AddProductActivityCompose::class.java)
                        intent.putExtra("LIST_ID", UUID.randomUUID().toString())
                        intent.putExtra("LIST_NAME", listName)
                        intent.putExtra("PRODUCTS", arrayListOf<Product>())
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(74.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC123), contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.button_confirm_create), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListScreenPreview() {
    ShopListTheme {
        CreateListScreen()
    }
}