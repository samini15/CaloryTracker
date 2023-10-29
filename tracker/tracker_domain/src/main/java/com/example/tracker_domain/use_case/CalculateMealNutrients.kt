package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences.UserInfoPreferences
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): NutrientsResult {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                MealNutrients(
                    mealType = type,
                    protein = foods.sumOf { it.protein },
                    carbs = foods.sumOf { it.carbs },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories }
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        // Nutrient Goals from User prefs
        val userInfo = preferences.loadUserInfo()

        val caloryGoal = dailyCaloryRequirement(userInfo = userInfo)
        val carbsGoal = (caloryGoal * userInfo.carbRatio / 4f).roundToInt() // 1g of Carbohydrate has 4 calories
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt() // 1g of Carbohydrate has 9 calories

        return NutrientsResult(
            proteinGoal = proteinGoal,
            carbsGoal = carbsGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalProtein = totalProtein,
            totalCarbs = totalCarbs,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    /**
     * Basic Metabolic Rate
     * Calculating number of calories burn by a person during a day
     */
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }
    data class MealNutrients(
        val mealType: MealType,
        val protein: Int,
        val carbs: Int,
        val fat: Int,
        val calories: Int
    )

    data class NutrientsResult(
        val proteinGoal: Int,
        val carbsGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,

        val totalProtein: Int,
        val totalCarbs: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}