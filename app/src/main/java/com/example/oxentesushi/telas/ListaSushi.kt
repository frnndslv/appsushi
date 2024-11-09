package com.example.oxentesushi.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.oxentesushi.data.AppDatabase
import com.example.oxentesushi.data.Sushi
import kotlinx.coroutines.launch

@Composable
fun ListaSushi(navController: NavHostController, sushiId: String?) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)


    val sushiIdInt = sushiId?.toIntOrNull()
    val sushiLiveData = if (sushiIdInt != null && sushiIdInt != -1) {
        db.sushiDao().getSushiById(sushiIdInt)
    } else {
        db.sushiDao().getSushiById(-1)
    }

    val sushi by sushiLiveData.observeAsState()

    var sushiName by remember { mutableStateOf(sushi?.name ?: "") }
    val isEdit = sushiId != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isEdit) "Editar Nome do Sushi" else "Novo prato",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(10.dp))

        TextField(
            value = sushiName,
            onValueChange = { sushiName = it },
            placeholder = { Text("Nome do Sushi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (isEdit) {
                        db.sushiDao().updateSushi(Sushi(id = sushiId?.toInt() ?: 0, name = sushiName))
                    } else {
                        db.sushiDao().insertSushi(Sushi(name = sushiName))
                    }
                }
                navController.navigate("menu") {
                    popUpTo("menu")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Salvar Alterações" else "Adicionar sushi")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("inicio") }
        ) {
            Text(text = "Voltar")
        }
    }
}
