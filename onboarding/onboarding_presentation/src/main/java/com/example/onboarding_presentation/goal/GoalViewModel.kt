package com.example.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.GoalType
import com.example.core.domain.preferences.Preferences
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val prefs: Preferences.UserInfoPreferences
): ViewModel() {
    var selectedGoal by mutableStateOf<GoalType>(GoalType.LoseWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelected(goal: GoalType) {
        selectedGoal = goal
    }

    fun onNextClick() = viewModelScope.launch {
        prefs.saveGoalType(type = selectedGoal)
        _uiEvent.send(UiEvent.Navigate)
    }
}