package com.example.mtgtourney

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Init navigation screen
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "poop") {
        composable(route = "poop") {
            PoopScreen(navController)
        }
        composable(route = "detail/{text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            DetailScreen(it.arguments?.getString("text")!!)
        }
    }
}


@Composable
fun PoopScreen(navController: NavController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Poop Home screen")

        Button(
            onClick = {
                navController.navigate("detail/detailedPoop")
            }, modifier = Modifier.padding(16.dp)
                .align(Alignment.CenterHorizontally),
        ) {

            Text(text = "Click Me")
        }
    }
}

@Composable
fun DetailScreen(text: String) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("welcome to detail page")
        Spacer(modifier = Modifier.padding(24.dp))
        Text("Text is $text")
    }
}