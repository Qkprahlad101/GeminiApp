package com.example.firstgeminiapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Utils.Result
import com.example.firstgeminiapp.domain.model.Summary
import com.example.firstgeminiapp.domain.usecase.GetSummaryUseCase
import com.example.common.ui.uiState.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetSummaryViewModel(private val getSummaryUseCase: GetSummaryUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Summary>>(UiState.Idle)
    val uiState: StateFlow<UiState<Summary>>
        get() = _uiState.asStateFlow()

    fun getSummary(inputText: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = getSummaryUseCase.invoke(inputText.trim())
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success(
                            Summary(
                                text = result.data.text,
                                originalTextLength = result.data.originalTextLength
                            )
                        )
                    }

                    is Result.Failure -> {
                        _uiState.value =
                            UiState.Failure<Summary>(Summary(error = result.error.error.toString()))
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure<Summary>(Summary(error = e.toString()))
            }
        }
    }

    fun reset() {
        _uiState.value = UiState.Idle
    }

}