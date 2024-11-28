package com.example.oxentesushi.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun InicioTela(navController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally

    ) {
        Text( text = "Oxente Sushi", style = MaterialTheme.typography.headlineLarge)
        Text( text = "Aplicativo do Funcionário", style = MaterialTheme.typography.headlineLarge)
        Button(
            onClick = {navController.navigate("login")}
        ){
            Text(text = "Login")
        }
        Button(
            onClick = {navController.navigate("menu")}
        ){
            Text(text = "Adicionar ao Menu")
        }

    }
}