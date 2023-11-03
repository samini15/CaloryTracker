package com.example.onboarding_presentation.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core.R
import com.example.core_ui.LocalSpacing
import com.example.core_ui.component.GradientBackgroundBrush
import com.example.onboarding_presentation.components.ActionButton

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = GradientBackgroundBrush(
                isVerticalGradient = true,
                colors = listOf(
                    androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    androidx.compose.material3.MaterialTheme.colorScheme.background
                )
            )
        )
        .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(spacing.spaceSmall),
                text = stringResource(id = R.string.welcome_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            ActionButton(
                text = stringResource(id = R.string.next),
                onClick = { onNextClick() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}