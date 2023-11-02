package com.example.tracker_presentation.search_food

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.remote.NetworkResult
import com.example.core.domain.use_case.FilterOutDigits
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.tracker_domain.use_case.TrackerUseCases
import com.example.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {
    var state by mutableStateOf(SearchFoodState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchFoodEvent) {
        when (event) {
            is SearchFoodEvent.OnAmountFromFoodChanged -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterOutDigits(event.amount))
                        } else it
                    }
                )
            }
            is SearchFoodEvent.OnQueryChanged -> state = state.copy(searchQuery = event.searchQuery)
            SearchFoodEvent.OnSearch -> {
                executeSearch()
            }
            is SearchFoodEvent.OnSearchActiveChanged -> {
                state = state.copy(
                    isHintVisible = !event.isActive && state.searchQuery.isBlank(),
                    isSearching = event.isActive
                )
            }
            is SearchFoodEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map {
                        if (it.food == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is SearchFoodEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun trackFood(event: SearchFoodEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFoods.find { it.food == event.food }

            trackerUseCases.trackFood(
                trackableFood = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoods = emptyList()
            )
            val networkResult = trackerUseCases
                .searchFood(query = state.searchQuery)
            when (networkResult) {
                is NetworkResult.Success -> {
                    state = state.copy(
                        trackableFoods = networkResult.data?.map {
                            TrackableFoodUiState(food = it)
                        } ?: emptyList(),
                        isSearching = false,
                        searchQuery = ""
                    )
                }
                is NetworkResult.Error -> {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnakbar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
            }
        }
    }
}