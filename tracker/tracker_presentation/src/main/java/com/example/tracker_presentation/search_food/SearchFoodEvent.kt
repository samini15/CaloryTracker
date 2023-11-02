package com.example.tracker_presentation.search_food

import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchFoodEvent {
    data class OnQueryChanged(val searchQuery: String): SearchFoodEvent()
    object OnSearch: SearchFoodEvent()
    data class OnToggleTrackableFood(val food: TrackableFood): SearchFoodEvent()
    data class OnAmountFromFoodChanged(val food: TrackableFood, val amount: String): SearchFoodEvent()
    data class OnTrackFoodClick(val food: TrackableFood, val mealType: MealType, val date: LocalDate): SearchFoodEvent()
    data class OnSearchActiveChanged(val isActive: Boolean): SearchFoodEvent()
}
