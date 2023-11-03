package com.example.tracker_presentation.tracker_overview.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.example.core_ui.CarbColor
import com.example.core_ui.FatColor
import com.example.core_ui.ProteinColor

@SuppressLint("RememberReturnType")
@Composable
fun NutrientsBar(
    modifier: Modifier = Modifier,
    carbs: Int,
    protein: Int,
    fat: Int,
    caloriesConsumed: Int,
    caloryGoal: Int
) {
    val background = MaterialTheme.colorScheme.background
    val caloriesExceededColor = MaterialTheme.colorScheme.error

    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRatio = remember {
        Animatable(0f)
    }
    val fatWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(targetValue = ((carbs * 4f) / caloryGoal)) // 1g of carb has 4 calories
    }
    LaunchedEffect(key1 = protein) {
        proteinWidthRatio.animateTo(targetValue = ((protein * 4f) / caloryGoal)) // 1g of protein has 4 calories
    }
    LaunchedEffect(key1 = fat) {
        fatWidthRatio.animateTo(targetValue = ((fat * 9f) / caloryGoal)) // 1g of fat has 9 calories
    }

    Canvas(modifier = modifier) {
        if (caloriesConsumed <= caloryGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width

            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = FatColor,
                size = Size(
                    width = carbsWidth + proteinWidth + fatWidth, // Because it is displayed under other values
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = ProteinColor,
                size = Size(
                    width = carbsWidth + proteinWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = CarbColor,
                size = Size(
                    width = carbsWidth, // Because it is displayed under other values
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesExceededColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}