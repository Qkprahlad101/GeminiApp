package com.example.firstgeminiapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.firstgeminiapp.ui.screen.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstgeminiapp.ui.screen.ConversationalScreen
import com.example.firstgeminiapp.ui.screen.TextSummaryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.CONVERSATIONAL.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.TEXT_SUMMARY.route) {
                TextSummaryScreen(modifier = Modifier)
            }
            composable(Screen.CONVERSATIONAL.route) {
                ConversationalScreen(modifier = Modifier)
            }
            composable(Screen.IMAGE_DESCRIPTION.route) {
                TextSummaryScreen(modifier = Modifier)
            }
            composable(Screen.IMAGE_GENERATION.route) {
                TextSummaryScreen(modifier = Modifier)
            }
        }
    }
}