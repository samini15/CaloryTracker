package com.example.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.use_case.FilterOutDigits
import com.example.core.util.UiEvent
import com.example.onboarding_domain.use_case.ValidateNutrientGoals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val prefs: Preferences.UserInfoPreferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrientGoals: ValidateNutrientGoals
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEntered ->
                this.state = state.copy(carbsRatio = filterOutDigits(event.ratio))

            is NutrientGoalEvent.OnFatRatioEntered ->
                this.state = state.copy(fatRatio = filterOutDigits(event.ratio))

            is NutrientGoalEvent.OnProteinRatioEntered ->
                this.state = state.copy(proteineRatio = filterOutDigits(event.ratio))

            NutrientGoalEvent.OnNextClicked -> {
                val result = validateNutrientGoals(
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteineRatio,
                    fatRatioText = state.fatRatio
                )
                when (result) {
                    is ValidateNutrientGoals.Result.Success -> {
                        prefs.apply {
                            saveCarbRatio(result.carbsRatio)
                            saveProteinRatio(result.proteinRatio)
                            saveFatRatio(result.fatRatio)
                        }
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Navigate)
                        }
                    }
                    is ValidateNutrientGoals.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnakbar(message = result.message))
                        }
                    }
                }
            }
        }
    }
}