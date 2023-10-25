package com.example.onboarding_presentation.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.GoalType
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core_ui.component.GradientBackgroundBrush
import com.example.onboarding_presentation.activity.ActivityViewModel
import com.example.onboarding_presentation.components.ActionButton
import com.example.onboarding_presentation.components.SelectableButton

@Composable
fun GoalScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = GradientBackgroundBrush(
                isVerticalGradient = true,
                colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background)
            )
        )
        .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(spacing.spaceLarge)) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = stringResource(id = R.string.lose_keep_or_gain_weight),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))


                    SelectableButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.lose),
                        isSelected = viewModel.selectedGoal is GoalType.LoseWeight,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    ) {
                        viewModel.onGoalSelected(goal = GoalType.LoseWeight)
                    }

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    SelectableButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.keep),
                        isSelected = viewModel.selectedGoal is GoalType.KeepWeight,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    ) {
                        viewModel.onGoalSelected(goal = GoalType.KeepWeight)
                    }

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    SelectableButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.gain),
                        isSelected = viewModel.selectedGoal is GoalType.GainWeight,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    ) {
                        viewModel.onGoalSelected(GoalType.GainWeight)
                    }
                }

            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}