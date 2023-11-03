package com.example.tracker_data.repository

import com.example.core.data.remote.NetworkResult
import com.example.tracker_data.local.TrackerDao
import com.example.tracker_data.mapper.toTrackableFood
import com.example.tracker_data.mapper.toTrackedFood
import com.example.tracker_data.mapper.toTrackedFoodEntity
import com.example.tracker_data.remote.OpenFoodApiService
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import com.example.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val trackerDao: TrackerDao,
    private val apiService: OpenFoodApiService
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): NetworkResult<List<TrackableFood>> {

        try {
            val searchRequest = apiService.searchFood(query = query, page = page, pageSize = pageSize)

            searchRequest.isSuccessful.apply {
                val searchDto = searchRequest.body()
                searchDto?.let {
                    return NetworkResult.Success(
                        data = searchDto.products
                            .filter {
                                val calculatedCalories = (it.nutriments.carbohydrates100g * 4f) + (it.nutriments.proteins100g * 4f) + (it.nutriments.fat100g * 9f)
                                val lowerBound = calculatedCalories * 0.99f
                                val upperBound = calculatedCalories * 1.01f
                                it.nutriments.energyKcal100g in (lowerBound..upperBound)
                            }
                            .map { it.toTrackableFood() }
                    )
                }
            }
            return NetworkResult.Error(searchRequest.message())

        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString(), data = null)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackerDao.insert(entity = food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackerDao.delete(entity = food.toTrackedFoodEntity())
    }

    override fun findFoodsForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return trackerDao.getFoodsForDate(
                day = date.dayOfMonth,
                month = date.monthValue,
                year = date.year
            ).map { entities ->
                entities.map { it.toTrackedFood() }
            }
    }
}