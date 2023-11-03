package com.example.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.tracker_presentation.components.ExpandableMealItem
import com.example.tracker_presentation.tracker_overview.components.AddButton
import com.example.tracker_presentation.tracker_overview.components.DaySelector
import com.example.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.example.tracker_presentation.tracker_overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item { 
            NutrientsHeader(state = state)
            
            DaySelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium),
                date = state.date,
                onPreviousDayClicked = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClicked = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                }
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }

        items(state.meals) { meal ->
            ExpandableMealItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceSmall),
                meal = meal,
                onToggleClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal)) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.spaceSmall),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val foodsForMealType = state.trackedFood.filter { it.mealType == meal.mealType }
                    foodsForMealType.forEach { food ->
                        TrackedFoodItem(
                            trackedFood = food,
                            onDeleteClicked = {
                                viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(food))
                            }
                        )

                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }

                    AddButton(
                        text = stringResource(
                            id = R.string.add_meal,
                            meal.name.asString(context)
                        )
                    ) {
                        viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(meal))
                    }
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                }
            }
        }
    }
}