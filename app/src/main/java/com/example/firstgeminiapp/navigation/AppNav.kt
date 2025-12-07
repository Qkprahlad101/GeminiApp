package com.example.firstgeminiapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
import com.example.common.ui.screen.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstgeminiapp.ui.screen.ConversationalScreen
import com.example.firstgeminiapp.ui.screen.HomeScreen
import com.example.firstgeminiapp.ui.screen.TextSummaryScreen
import com.example.imagen.ui.screen.GetImageFromPrompt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.HOME.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.HOME.route) {
                HomeScreen(modifier = Modifier, navController = navController)
            }
            composable(Screen.TEXT_SUMMARY.route) {
                TextSummaryScreen(modifier = Modifier)
            }
            composable(Screen.CONVERSATIONAL.route) {
                ConversationalScreen(modifier = Modifier)
            }
            composable(Screen.IMAGE_DESCRIPTION.route) {
//                TextSummaryScreen(modifier = Modifier)
                Text(
                    text = "Feature Coming Soon!",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(Screen.IMAGE_GENERATION.route) {
                GetImageFromPrompt()
            }
        }
    }
}