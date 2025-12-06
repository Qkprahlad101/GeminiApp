package com.example.firstgeminiapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstgeminiapp.ui.viewModel.GetSummaryViewModel
import com.example.firstgeminiapp.ui.viewModel.UiState
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(viewModel: GetSummaryViewModel = koinViewModel(), modifier: Modifier) {

    val uiState by viewModel.uiState.collectAsState()

    var promptText by remember { mutableStateOf("")}

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when(val state = uiState) {
            is UiState.Idle -> {
                Text(
                    text = "Enter a prompt and click button to start",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                OutlinedTextField(
                    value = promptText,
                    onValueChange = { promptText = it},
                    label = {Text("Enter the text you want to summarise!")},
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.getSummary(promptText)
                }) {
                    Text(text = "Get Summary")
                }
            }
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                Text(
                    text = "Summary Result: ${state.data.text}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Button(onClick = {
                    promptText = ""
                    viewModel.reset()
                }) {
                    Text(text = "Reset")
                }
            }
            is UiState.Failure -> {
                Text(
                    text = "Error: ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

}