package com.example.core.util

/**
 * Define events (one-time) that ViewModel sends to a composable (View)
 */
sealed class UiEvent {
    object Navigate: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnakbar(val message: UiText): UiEvent()
}