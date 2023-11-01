package com.example.onboarding_presentation.gender

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.core.domain.model.Gender
import com.example.core_ui.component.GradientBackgroundBrush
import com.example.onboarding_presentation.components.ActionButton
import com.example.onboarding_presentation.components.SelectableButton

@Composable
fun GenderScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
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
                        text = stringResource(id = R.string.whats_your_gender),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))


                    SelectableButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.male),
                        isSelected = viewModel.selectedGender is Gender.Male,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    ) {
                        viewModel.onGenderSelected(Gender.Male)
                    }

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    SelectableButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.female),
                        isSelected = viewModel.selectedGender is Gender.Female,
                        color = MaterialTheme.colorScheme.primary,
                        selectedTextColor = Color.White,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
                    ) {
                        viewModel.onGenderSelected(Gender.Female)
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

@Preview
@Composable
fun GenderScreenPreview() {
    GenderScreen(onNavigate = {})
}