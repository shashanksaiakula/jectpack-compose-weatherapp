package com.example.weatherapp.utils

import com.example.weatherapp.api.UiState

class ErrorHandler<T>(private var uiState: (UiState<T>) -> Unit) {
    fun handleApiError(code: Int, message: String) {
        val errorState = when (code) {
            400 -> UiState.Error("Error 400: Bad Request.")
            401 -> UiState.Error("Error 401: Unauthorized.")
            403 -> UiState.Error("Error 403: Forbidden.")
            404 -> UiState.Error("Error 404: Not Found.")
            500 -> UiState.Error("Error 500: Internal Server Error.")
            else -> UiState.Error("Error $code: $message.")
        }
        uiState(errorState) // Update the UI state using the provided lambda
    }
}
