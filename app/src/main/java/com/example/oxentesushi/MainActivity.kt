package com.example.oxentesushi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oxentesushi.data.cardapio.CardapioDatabase.Companion.abrirCardapioBancoDeDados
import com.example.oxentesushi.data.cardapio.LocalCardapioRepository
import com.example.oxentesushi.data.cardapio.RemoteCardapioRepository
import com.example.oxentesushi.data.usuario.LocalRepository
import com.example.oxentesushi.data.usuario.RemoteRepository
import com.example.oxentesushi.data.usuario.UsuarioDatabase.Companion.abrirBancoDeDados
import com.example.oxentesushi.telas.CriarConta
import com.example.oxentesushi.telas.InicioTela
import com.example.oxentesushi.telas.ListaSushi
import com.example.oxentesushi.telas.Login
import com.example.oxentesushi.telas.MenuSushi
import com.example.oxentesushi.ui.theme.OxenteSushiTheme
import com.example.oxentesushi.ui.viewmodel.CardapioViewModel
import com.example.oxentesushi.ui.viewmodel.UsuarioViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        val db = abrirBancoDeDados(this)
        val cardapioDb = abrirCardapioBancoDeDados(this)
        //Cardapio
        val localCardapioDatabase = LocalCardapioRepository(cardapioDb.cardapioDao())
        val remoteCardapioRepository = RemoteCardapioRepository()
        //Usuario
        val localRepository = LocalRepository(db.usuarioDao())
        val remoteRepository = RemoteRepository()

        val cardapioViewModel = CardapioViewModel(remoteCardapioRepository)
        val viewModel = UsuarioViewModel(remoteRepository)

        setContent {
            OxenteSushiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TelaTotal(viewModel, cardapioViewModel)
                }
            }
        }
    }
}


@Composable
fun TelaTotal(viewModel: UsuarioViewModel, cardapioViewModel: CardapioViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { InicioTela(navController) }
        composable("login") { Login(navController, viewModel) }
        composable("criarConta") { CriarConta(navController, viewModel) }
        composable("menu") { MenuSushi(navController, cardapioViewModel) }
        composable("listasushi/{sushiId}") { backStackEntry ->
            val sushiId = backStackEntry.arguments?.getString("sushiId")
            Log.d("sushiu 1--", "$sushiId")
            ListaSushi(navController, sushiId)

        }
    }
}