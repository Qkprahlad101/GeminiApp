package com.example.imagen.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Utils.Result
import com.example.common.ui.uiState.UiState
import com.example.imagen.data.model.ImageResponse
import com.example.imagen.domain.usecase.GetImageFromPromptUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetImageFromGeminiViewModel(private val getImageFromPromptUseCase: GetImageFromPromptUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ImageResponse>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getImageFromPrompt(prompt: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading
            try {
                val result = getImageFromPromptUseCase(prompt)
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success(result.data)
                    }

                    is Result.Failure -> {
                        _uiState.value = UiState.Failure(result.error)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure<ImageResponse>(ImageResponse(error = e.message))
            }
        }
    }
}