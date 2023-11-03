package com.example.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.R
import com.example.core.domain.preferences.Preferences
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val prefs: Preferences.UserInfoPreferences
) : ViewModel() {
    var weight by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEntered(weight: String) {
        if (weight.length <= 5) {
            this.weight = weight
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val weightNumber = weight.toFloatOrNull() ?: kotlin.run {
            _uiEvent.send(
                UiEvent.ShowSnakbar(UiText.StringResource(R.string.error_weight_cant_be_empty))
            )
            return@launch
        }
        prefs.saveWeight(weight = weightNumber)
        _uiEvent.send(UiEvent.Navigate)
    }
}