package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() {
        val preferences = mockk<Preferences.UserInfoPreferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 26,
            weight = 85f,
            height = 186,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.2f,
            proteinRatio = 0.6f,
            fatRatio = 0.2f
        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        // Random list of 20 foods
        val trackedFoods = (1..20).map {
            TrackedFood(
                name = "Apple",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "snack", "dinner").random()
                ),
                imageUrl = null,
                amount = 10,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrients(trackedFoods)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedBreakfastCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        Truth.assertThat(breakfastCalories).isEqualTo(expectedBreakfastCalories)
    }

    @Test
    fun `Fat for lunch properly calculated`() {
        // Random list of 20 foods
        val trackedFoods = (1..20).map {
            TrackedFood(
                name = "Apple",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "snack", "dinner").random()
                ),
                imageUrl = null,
                amount = 10,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrients(trackedFoods)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.fat }

        val expectedBreakfastCalories = trackedFoods
            .filter { it.mealType is MealType.Lunch }
            .sumOf { it.fat }

        Truth.assertThat(breakfastCalories).isEqualTo(expectedBreakfastCalories)
    }
}