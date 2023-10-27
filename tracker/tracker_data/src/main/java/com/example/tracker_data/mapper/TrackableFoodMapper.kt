package com.example.tracker_data.mapper

import com.example.tracker_data.remote.dto.Product
import com.example.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val calories100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = productName,
        imageUrl = imageFrontThumbUrl,
        caloriesPer100g = calories100g,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g
    )
}