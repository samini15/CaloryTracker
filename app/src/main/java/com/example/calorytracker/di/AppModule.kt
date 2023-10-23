package com.example.calorytracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.data.preferences.UserInfoPreferences
import com.example.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(
        @ApplicationContext app: Application
    ): SharedPreferences =
        app.getSharedPreferences("shared_prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserInfoPrefs(sharedPreferences: SharedPreferences): Preferences.UserInfoPreferences =
        UserInfoPreferences(sharedPref = sharedPreferences)
}