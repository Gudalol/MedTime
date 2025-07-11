package com.example.gildoapplication2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gildoapplication2.ui.screens.ListaViewModel
import com.example.gildoapplication2.ui.screens.TelaCriarItem
import com.example.gildoapplication2.ui.screens.TelaPrincipal
import com.example.gildoapplication2.ui.theme.GildoApplication2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ListaViewModel()

        setContent {
            GildoApplication2Theme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = "principal",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("principal") {
                            TelaPrincipal().TelaPrincipal(
                                viewModel = viewModel,
                                onIrParaCriar = {
                                    navController.navigate("criar")
                                }
                            )
                        }
                        composable("criar") {
                            TelaCriarItem().TelaComTopAppBar(
                                viewModel = viewModel,
                                onVoltar = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
