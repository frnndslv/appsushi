package com.example.oxentesushi.telas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.oxentesushi.ui.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun Login (navController: NavHostController, viewModel: UsuarioViewModel) {
    val context = LocalContext.current // Obter o contexto atual
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally

    ) {
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Login") }
        )
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
        )

        Row {
            Button(
                onClick = {
                    viewModel.viewModelScope.launch {
                        val check = viewModel.buscarPorLoginESenha(login, senha)
                        if (check) {
                            // Autenticação bem-sucedida, prossiga

                            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                            navController.navigate("menu")
                        } else {
                            // Falha na autenticação
                            Toast.makeText(context, "Falha no login. Tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ){
                Text(text = "Logar")
            }
            Button(
                onClick = {navController.navigate("criarConta")}
            ){
                Text(text = "Criar Conta")
            }
        }

    }
}