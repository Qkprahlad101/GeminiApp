package com.example.imagen.ui.screen

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.imagen.ui.viewModel.GetImageFromGeminiViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.common.ui.uiState.UiState

@Composable
fun GetImageFromPrompt(viewModel: GetImageFromGeminiViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    var promptText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                Text(
                    text = "Get Image From Prompt",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    ) { innerPadding ->

        when(uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Failure -> {
                Text(text = (uiState as UiState.Failure).error.error.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(innerPadding).size(64.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
            is UiState.Success -> {
                if (uiState is UiState.Success) {
                    val imageResponse = (uiState as UiState.Success).data
                    imageResponse?.data?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Generated Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            is UiState.Idle -> {
                Column {
                    OutlinedTextField(
                        value = promptText,
                        onValueChange = { promptText = it },
                        label = { Text("Enter your prompt") },
                        modifier = Modifier.fillMaxWidth().padding(innerPadding)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.getImageFromPrompt(promptText) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get Image")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }

}
