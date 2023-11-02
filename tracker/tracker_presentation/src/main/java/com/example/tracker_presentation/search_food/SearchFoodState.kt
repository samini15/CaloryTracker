package com.example.tracker_presentation.search_food

data class SearchFoodState(
    val searchQuery: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFoods: List<TrackableFoodUiState> = emptyList()
)
