package com.example.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.R
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core_ui.component.GradientBackgroundBrush
import com.example.onboarding_presentation.components.ActionButton
import com.example.onboarding_presentation.height.HeightViewModel

@Composable
fun NutrientGoalScreen(
    snakbarState: SnackbarHostState,
    onNextClick: () -> Unit,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNextClick()
                is UiEvent.ShowSnakbar -> {
                    snakbarState.showSnackbar(message = event.message.asString(context = context))
                }
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = GradientBackgroundBrush(
                isVerticalGradient = true,
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.background
                )
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
                        text = stringResource(id = R.string.what_are_your_nutrient_goals),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))


                    OutlinedTextField(
                        value = viewModel.state.carbsRatio,
                        onValueChange = {
                            viewModel.onEvent(NutrientGoalEvent.OnCarbRatioEntered(ratio = it))
                        },
                        label = { Text(text = "Carb ratio") },
                        trailingIcon = {
                            Text(
                                text = stringResource(id = R.string.percent_carbs),
                                modifier = Modifier.padding(spacing.spaceMedium),
                                fontWeight = FontWeight.Light
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    OutlinedTextField(
                        value = viewModel.state.proteineRatio,
                        onValueChange = {
                            viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEntered(ratio = it))
                        },
                        label = { Text(text = "Protein Ratio") },
                        trailingIcon = {
                            Text(
                                text = stringResource(id = R.string.percent_proteins),
                                modifier = Modifier.padding(spacing.spaceMedium),
                                fontWeight = FontWeight.Light
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    OutlinedTextField(
                        value = viewModel.state.fatRatio,
                        onValueChange = {
                            viewModel.onEvent(NutrientGoalEvent.OnFatRatioEntered(ratio = it))
                        },
                        label = { Text(text = "Fat Ratio") },
                        trailingIcon = {
                            Text(
                                text = stringResource(id = R.string.percent_fats),
                                modifier = Modifier.padding(spacing.spaceMedium),
                                fontWeight = FontWeight.Light
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                }

            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onEvent(NutrientGoalEvent.OnNextClicked)
            },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}