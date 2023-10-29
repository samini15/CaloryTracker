package com.example.tracker_domain.model

import java.time.LocalDate

/**
 * For Clean Architecture
 * Wrapper class to wrap the data layer entity in the domain layer
 */
data class TrackedFood(
    val id: Int = 0,
    val name: String,
    val imageUrl: String?,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int
)
