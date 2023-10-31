package com.example.core.data.preferences

import android.content.SharedPreferences
import com.example.core.domain.preferences.Preferences

class OnboardingPreferences(
    private val sharedPref: SharedPreferences
): Preferences.OnboardingPreferences {
    override fun saveShouldShowOnboarding(shouldShow: Boolean) = sharedPref.edit()
        .putBoolean(Preferences.OnboardingPreferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
        .apply()

    override fun loadShouldShowOnboarding(): Boolean =
        sharedPref.getBoolean(Preferences.OnboardingPreferences.KEY_SHOULD_SHOW_ONBOARDING, true)

}