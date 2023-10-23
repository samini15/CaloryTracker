package com.example.core.data.preferences

import android.content.SharedPreferences
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences

class UserInfoPreferences(
    private val sharedPref: SharedPreferences
): Preferences.UserInfoPreferences {
    override fun saveGender(gender: Gender) = sharedPref.edit()
        .putString(Preferences.UserInfoPreferences.KEY_GENDER, gender.name)
        .apply()

    override fun saveAge(age: Int) = sharedPref.edit()
        .putInt(Preferences.UserInfoPreferences.KEY_AGE, age)
        .apply()

    override fun saveWeight(weight: Float) = sharedPref.edit()
        .putFloat(Preferences.UserInfoPreferences.KEY_WEIGHT, weight)
        .apply()

    override fun saveHeight(height: Int) = sharedPref.edit()
        .putInt(Preferences.UserInfoPreferences.KEY_HEIGHT, height)
        .apply()

    override fun saveActivityLevel(level: ActivityLevel) = sharedPref.edit()
        .putString(Preferences.UserInfoPreferences.KEY_ACTIVITY_LEVEL, level.name)
        .apply()

    override fun saveGoalType(type: GoalType) = sharedPref.edit()
        .putString(Preferences.UserInfoPreferences.KEY_GOAL_TYPE, type.name)
        .apply()

    override fun saveCarbRatio(ratio: Float) = sharedPref.edit()
        .putFloat(Preferences.UserInfoPreferences.KEY_CARB_RATIO, ratio)
        .apply()

    override fun saveProteinRatio(ratio: Float) = sharedPref.edit()
        .putFloat(Preferences.UserInfoPreferences.KEY_PROTEIN_RATIO, ratio)
        .apply()

    override fun saveFatRatio(ratio: Float) = sharedPref.edit()
        .putFloat(Preferences.UserInfoPreferences.KEY_FAT_RATIO, ratio)
        .apply()

    override fun loadUserInfo(): UserInfo {
        val userInfo: UserInfo
        sharedPref.apply {
            val genderString = getString(Preferences.UserInfoPreferences.KEY_GENDER, null)
            val age = getInt(Preferences.UserInfoPreferences.KEY_AGE, -1)
            val weight = getFloat(Preferences.UserInfoPreferences.KEY_WEIGHT, -1f)
            val height = getInt(Preferences.UserInfoPreferences.KEY_HEIGHT, -1)
            val activityLevelString = getString(Preferences.UserInfoPreferences.KEY_ACTIVITY_LEVEL, null)
            val goalTypeString = getString(Preferences.UserInfoPreferences.KEY_GOAL_TYPE, null)
            val carbRatio = getFloat(Preferences.UserInfoPreferences.KEY_CARB_RATIO, -1f)
            val proteinRatio = getFloat(Preferences.UserInfoPreferences.KEY_PROTEIN_RATIO, -1f)
            val fatRatio = getFloat(Preferences.UserInfoPreferences.KEY_FAT_RATIO, -1f)

            userInfo = UserInfo(
                gender = Gender.fromString(genderString ?: "Female"),
                age = age,
                weight = weight,
                height = height,
                activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
                goalType = GoalType.fromString(goalTypeString ?: "gain_weight"),
                carbRatio = carbRatio,
                proteinRatio = proteinRatio,
                fatRatio = fatRatio
            )
        }
        return userInfo
    }
}