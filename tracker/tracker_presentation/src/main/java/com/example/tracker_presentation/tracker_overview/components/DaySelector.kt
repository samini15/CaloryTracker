package com.example.tracker_presentation.tracker_overview.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R
import java.time.LocalDate

@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onPreviousDayClicked: () -> Unit,
    onNextDayClicked: () -> Unit
) {
    Row(
        modifier = modifier, 
        horizontalArrangement = Arrangement.SpaceBetween, 
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousDayClicked) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.previous_day))
        }

        Text(
            text = ParseDateText(date = date),
            style = MaterialTheme.typography.headlineMedium
        )

        IconButton(onClick = onNextDayClicked) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = stringResource(id = R.string.next_day))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DaySelectorPreview() {
    DaySelector(date = LocalDate.now().minusDays(2), onPreviousDayClicked = { }, onNextDayClicked = { })
}