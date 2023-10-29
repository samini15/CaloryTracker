package com.example.tracker_domain.use_case

data class TrackerUseCases(
    val trackFood: TrackFood,
    val deleteTrackedFood: DeleteTrackedFood,
    val searchFood: SearchFood,
    val calculateMealNutrients: CalculateMealNutrients,
    val getFoodsForDate: GetFoodsForDate
)
