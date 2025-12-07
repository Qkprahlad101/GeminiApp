package com.example.firstgeminiapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firstgeminiapp.domain.model.Message
import com.example.firstgeminiapp.ui.UiState
import com.example.firstgeminiapp.ui.viewModel.ConversationalViewModel
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun ConversationalScreen(
    conversationalViewModel: ConversationalViewModel = koinViewModel(),
    modifier: Modifier
) {

    val uiState by conversationalViewModel.uiState.collectAsState()
    val conversationList by conversationalViewModel.conversations.collectAsState()
    var promptText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.size(8.dp)
                )
                TopAppBar(
                    title = { Text("Conversational") }
                )
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (val state = uiState) {
                    is UiState.Idle -> {
                        if (conversationList.isNotEmpty()) {
                            items(conversationList.reversed()) { item ->
                                MessageItem(item)
                            }
                        }
                    }
                    is UiState.Loading -> {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("AI is thinking...")
                            }
                        }
                        items(conversationList.reversed()) { item ->
                            MessageItem(item)
                        }
                    }
                    is UiState.Success -> {
                        item {
                            MessageItem(
                                Message(
                                    message = state.data.message,
                                    isUser = false
                                )
                            )
                        }
                        items(conversationList.reversed()) { item ->
                            MessageItem(item)
                        }
                    }
                    is UiState.Failure -> {
                        item {
                            MessageItem(
                                Message(
                                    error = state.error.error.toString(),
                                    isUser = false
                                )
                            )
                        }
                        items(conversationList.reversed()) { item ->
                            MessageItem(item)
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = promptText,
                    onValueChange = { promptText = it },
                    label = { Text("Enter your question") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    if (promptText.isNotBlank()) {
                        conversationalViewModel.getAnswer(promptText)
                        promptText = ""
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    val isUserMsg = message.isUser
    val content = message.message ?: message.error ?: return
    val isError = message.error != null
    val textColor = if (isError) {
        MaterialTheme.colorScheme.onError
    } else if (isUserMsg) {
        Color.Black
    } else {
        Color.Gray
    }
    val containerColor = if (isError) {
        MaterialTheme.colorScheme.errorContainer
    } else if (isUserMsg) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUserMsg) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = containerColor,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = content,
                color = textColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}