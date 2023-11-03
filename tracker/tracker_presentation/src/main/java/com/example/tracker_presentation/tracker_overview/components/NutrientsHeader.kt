package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.core_ui.CarbColor
import com.example.core_ui.FatColor
import com.example.core_ui.ProteinColor
import com.example.tracker_presentation.components.UnitDisplay
import com.example.tracker_presentation.tracker_overview.TrackerOverviewState

@Composable
fun NutrientsHeader(
    modifier: Modifier = Modifier,
    state: TrackerOverviewState
) {
    val spacing = LocalSpacing.current

    val animatedCalorieCount = animateIntAsState(targetValue = state.totalCalories, label = "")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = spacing.spaceLarge,
                vertical = spacing.spaceExtraLarge
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                modifier = Modifier.align(Alignment.Bottom),
                amount = animatedCalorieCount.value,
                unit = stringResource(id = R.string.kcal),
                amountColor = MaterialTheme.colorScheme.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colorScheme.onPrimary
            )

            Column {
                Text(
                    text = stringResource(id = R.string.your_goal),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                UnitDisplay(
                    amount = state.caloriesGoal,
                    unit = stringResource(id = R.string.kcal),
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        NutrientsBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            carbs = state.totalCarbs,
            protein = state.totalProtein,
            fat = state.totalFat,
            caloriesConsumed = state.totalCalories,
            caloryGoal = state.caloriesGoal
        )
        
        Spacer(modifier = Modifier.height(spacing.spaceLarge))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutrientsBarInfo(
                modifier = Modifier.size(90.dp),
                value = state.totalCarbs,
                goal = state.carbsGoal,
                name = stringResource(id = R.string.carbs),
                color = CarbColor
            )

            NutrientsBarInfo(
                modifier = Modifier.size(90.dp),
                value = state.totalProtein,
                goal = state.proteinGoal,
                name = stringResource(id = R.string.protein),
                color = ProteinColor
            )

            NutrientsBarInfo(
                modifier = Modifier.size(90.dp),
                value = state.totalFat,
                goal = state.fatGoal,
                name = stringResource(id = R.string.fat),
                color = FatColor
            )
        }
    }
}