package com.example.tracker_domain.di

import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.repository.TrackerRepository
import com.example.tracker_domain.use_case.CalculateMealNutrients
import com.example.tracker_domain.use_case.DeleteTrackedFood
import com.example.tracker_domain.use_case.GetFoodsForDate
import com.example.tracker_domain.use_case.SearchFood
import com.example.tracker_domain.use_case.TrackFood
import com.example.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences.UserInfoPreferences
    ): TrackerUseCases =
        TrackerUseCases(
            trackFood = TrackFood(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            searchFood = SearchFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences),
            getFoodsForDate = GetFoodsForDate(repository)
        )
}