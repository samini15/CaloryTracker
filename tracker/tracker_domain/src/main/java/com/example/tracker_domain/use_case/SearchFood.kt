package com.example.tracker_domain.use_case

import com.example.core.data.remote.NetworkResult
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.repository.TrackerRepository

class SearchFood(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): NetworkResult<List<TrackableFood>> {
        if (query.isBlank()) {
            return NetworkResult.Success(data = emptyList())
        }
        return repository.searchFood(query = query.trim(), page = page, pageSize = pageSize)
    }
}