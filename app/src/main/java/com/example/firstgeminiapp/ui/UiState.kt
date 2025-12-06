package com.example.firstgeminiapp.ui

import com.example.firstgeminiapp.domain.model.Summary

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: Summary) : UiState()
    data class Failure(val error: String) : UiState()
}