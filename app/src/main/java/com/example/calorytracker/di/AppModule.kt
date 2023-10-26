package com.example.calorytracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.data.preferences.UserInfoPreferences
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.use_case.FilterOutDigits
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(
        app: Application
    ): SharedPreferences =
        app.getSharedPreferences("shared_prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserInfoPrefs(sharedPreferences: SharedPreferences): Preferences.UserInfoPreferences =
        UserInfoPreferences(sharedPref = sharedPreferences)


    // Use cases
    @Provides
    @Singleton
    fun provideFilterOutDigitsUseCase(): FilterOutDigits = FilterOutDigits()
}