package com.example.oxentesushi.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.oxentesushi.data.AppDatabase
import com.example.oxentesushi.data.Sushi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MenuSushi(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)


    val sushiLiveData: LiveData<List<Sushi>> = db.sushiDao().getAllSushi()
    val sushiList by sushiLiveData.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Text("Adicione um prato ao menu do restaurante", style = MaterialTheme.typography.bodyLarge)
        Text("Caso deseje editar por aqui, clique sobre o prato desejado", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(26.dp))


        SushiInputField { sushiName ->
            coroutineScope.launch {
                db.sushiDao().insertSushi(Sushi(name = sushiName))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn {
            items(sushiList) { sushi ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {

                    Button(
                        onClick = { navController.navigate("listaSushi/${sushi.id}") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = sushi.name)
                    }
                    Spacer(modifier = Modifier.height(4.dp))


                    Button(
                        onClick = {
                            coroutineScope.launch {
                                db.sushiDao().deleteSushi(sushi)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.33f)
                            .background(Color.Red),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Deletar")
                    }

                }
            }
        }
    }
}

@Composable
fun SushiInputField(onAddSushi: (String) -> Unit) {
    var newSushi by remember { mutableStateOf("") }
    Column {
        TextField(
            value = newSushi,
            onValueChange = { newSushi = it },
            placeholder = { Text("Digite o nome do prato") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (newSushi.isNotBlank()) {
                    onAddSushi(newSushi)
                    newSushi = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }
    }
}

