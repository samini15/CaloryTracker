package com.example.tracker_domain.model

/**
 * For Clean Architecture
 * Wrapper class to wrap the API result in the domain layer
 */
data class TrackableFood(
    val name: String,
    val imageUrl: String?,
    val caloriesPer100g: Int,
    val carbsPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int
)
