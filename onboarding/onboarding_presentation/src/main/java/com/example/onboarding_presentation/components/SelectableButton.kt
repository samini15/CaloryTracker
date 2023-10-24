package com.example.onboarding_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.core_ui.LocalSpacing

@Composable
fun SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    color: Color,
    selectedTextColor: Color,
    textStyle: TextStyle = MaterialTheme.typography.displayMedium,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(width = 2.dp, color = color, shape = RoundedCornerShape(10.dp))
            .background(
                color = if (isSelected) color else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(LocalSpacing.current.spaceSmall)
    ) {
        Text(
            modifier = Modifier.padding(LocalSpacing.current.spaceSmall),
            text = text,
            style = textStyle,
            color = if (isSelected) selectedTextColor else color
        )
    }
}