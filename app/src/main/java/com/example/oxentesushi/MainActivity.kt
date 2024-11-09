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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oxentesushi.telas.InicioTela
import com.example.oxentesushi.telas.ListaSushi
import com.example.oxentesushi.telas.MenuSushi
import com.example.oxentesushi.ui.theme.OxenteSushiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OxenteSushiTheme {
               Surface(
                   modifier = Modifier.fillMaxSize()
               ) {
                   TelaTotal()
               }

                }
            }
        }
    }


@Composable
fun TelaTotal() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { InicioTela(navController) }
        composable("menu") { MenuSushi(navController) }
        composable("listasushi/{sushiId}") { backStackEntry ->
            val sushiId = backStackEntry.arguments?.getString("sushiId")
            Log.d("sushiu 1--", "$sushiId")
            ListaSushi(navController, sushiId)

        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OxenteSushiTheme {
    Surface(modifier = Modifier.fillMaxSize()){
        TelaTotal()
         }
    }
}