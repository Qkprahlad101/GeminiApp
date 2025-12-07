package com.example.firstgeminiapp.ui.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(modifier: Modifier, navController: NavController) {
    val scrollable = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Gemini App")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Screen.entries.filter { it.route != Screen.HOME.route }.forEach { screen ->
               item {
                   FeatureItem(
                       screen, navController
                   )
               }
            }
        }
    }
}

@Composable
fun FeatureItem(screen: Screen, navController: NavController) {
    Button(
        onClick = { navController.navigate(screen.route) }
    ) {
        Text(text = screen.route)
    }
}
