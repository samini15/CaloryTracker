package com.example.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.local.BaseDao
import com.example.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao: BaseDao<TrackedFoodEntity> {

    @Query(
        """
            SELECT *
            FROM trackedfoodentity
            WHERE dayOfMonth = :day
            AND month = :month
            AND year = :year
        """
    )
    fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}