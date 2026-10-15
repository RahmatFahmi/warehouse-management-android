package com.rahmat.project1.ui.common

sealed interface UiState<out T> {
    object Idle : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    object Empty : UiState<Nothing>
    object NoInternet : UiState<Nothing>
    object Timeout : UiState<Nothing>
    data class Error(val message: String) : UiState<Nothing>
}
