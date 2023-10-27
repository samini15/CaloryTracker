package com.example.tracker_domain.repository

import com.example.core.data.remote.NetworkResult
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): NetworkResult<List<TrackableFood>>

    // Local data
    suspend fun insertTrackedFood(food: TrackedFood)

    suspend fun deleteTrackedFood(food: TrackedFood)

    fun findFoodsForDate(date: LocalDate): Flow<List<TrackedFood>>
}