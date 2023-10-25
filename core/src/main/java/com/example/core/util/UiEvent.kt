package com.example.core.util

/**
 * Define events (one-time) that ViewModel sends to a composable (View)
 */
sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnakbar(val message: UiText): UiEvent()
}