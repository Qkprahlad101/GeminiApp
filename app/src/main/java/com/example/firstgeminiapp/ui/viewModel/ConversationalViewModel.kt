package com.example.firstgeminiapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstgeminiapp.domain.Result
import com.example.firstgeminiapp.domain.model.Message
import com.example.firstgeminiapp.domain.usecase.ConversationUseCase
import com.example.firstgeminiapp.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ConversationalViewModel(private val conversationUseCase: ConversationUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Message>>(UiState.Idle as UiState<Message>)
    val uiState: StateFlow<UiState<Message>>
        get() = _uiState.asStateFlow()

    private val _conversations = MutableStateFlow<List<Message>>(emptyList())
    val conversations: StateFlow<List<Message>>
        get() = _conversations.asStateFlow()

    fun getAnswer(inputText: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = conversationUseCase.invoke(inputText.trim())
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success<Message>(
                            data = Message(
                                message = result.data.message,
                                isUser = false
                            )
                        )
                        _conversations.value = _conversations.value + Message(
                            message = result.data.message,
                            isUser = false
                        )
                    }

                    is Result.Failure -> {
                        _uiState.value =
                            UiState.Failure<Message>(error = Message(error = result.error.error.toString()))
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure<Message>(error = Message(error = e.toString()))
            }
        }
    }

    fun addUserMessage(text: String) {
        _conversations.value = _conversations.value + Message(
            message = text,
            isUser = true
        )
    }

    fun reset() {
        _uiState.value = UiState.Idle
        _conversations.value = emptyList()
    }

}