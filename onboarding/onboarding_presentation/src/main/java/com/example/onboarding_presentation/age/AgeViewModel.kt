package com.example.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.use_case.FilterOutDigits
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val prefs: Preferences.UserInfoPreferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var age by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEntered(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val ageNumber = age.toIntOrNull() ?: kotlin.run {
            _uiEvent.send(
                UiEvent.ShowSnakbar(UiText.StringResource(R.string.error_age_cant_be_empty))
            )
            return@launch
        }
        prefs.saveAge(age = ageNumber)
        _uiEvent.send(UiEvent.Navigate)
    }
}