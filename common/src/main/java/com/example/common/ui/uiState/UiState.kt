package com.example.common.ui.uiState

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Failure<out T>(val error: T) : UiState<T>()
}