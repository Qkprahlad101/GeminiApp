package com.example.firstgeminiapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstgeminiapp.domain.Result
import com.example.firstgeminiapp.domain.model.Summary
import com.example.firstgeminiapp.domain.usecase.GetSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UiState {
    object Idle: UiState()
    object Loading: UiState()
    data class Success(val data: Summary) : UiState()
    data class Failure(val error: String): UiState()
}
class GetSummaryViewModel(private val getSummaryUseCase: GetSummaryUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    fun getSummary(inputText: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = getSummaryUseCase.invoke(inputText.trim())
               when(result) {
                   is Result.Sucesss -> {
                       _uiState.value = UiState.Success(Summary(text = result.data.text, originalTextLength = result.data.originalTextLength))
                   }
                   is Result.Failure -> {
                       _uiState.value = UiState.Failure(error = result.error.error.toString())
                   }
               }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure(error = e.toString())
            }
        }
    }

    fun reset() {
        _uiState.value = UiState.Idle
    }

}