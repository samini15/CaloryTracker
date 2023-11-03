package com.example.tracker_presentation.search_food.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.tracker_presentation.components.NutrientInfo
import com.example.tracker_presentation.search_food.TrackableFoodUiState

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackableFoodItem(
    modifier: Modifier = Modifier,
    trackableFoodUiState: TrackableFoodUiState,
    onAmountChange: (String) -> Unit,
    onClick: () -> Unit,
    onTrack: () -> Unit
) {
    val spacing = LocalSpacing.current

    val food = trackableFoodUiState.food


    Column(modifier = modifier) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = modifier
                    .padding(spacing.spaceSmall)
                    .clickable { onClick() }
                    .padding(end = spacing.spaceMedium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        Image(
                            modifier = Modifier
                                .size(100.dp),
                            painter = rememberImagePainter(
                                data = food.imageUrl,
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ic_dinner)
                                }
                            ),
                            contentDescription = food.name,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))

                        Column(modifier = Modifier.align(CenterVertically)) {
                            Text(
                                text = food.name,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            
                            Spacer(modifier = Modifier.height(spacing.spaceSmall))
                            
                            Text(
                                text = stringResource(
                                    id = R.string.kcal_per_100g,
                                    food.caloriesPer100g
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }


                    }

                    Row {
                        NutrientInfo(
                            name = stringResource(id = R.string.carbs),
                            amount = food.carbsPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.bodySmall
                        )
                        
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            name = stringResource(id = R.string.protein),
                            amount = food.proteinPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.width(spacing.spaceSmall))

                        NutrientInfo(
                            name = stringResource(id = R.string.fat),
                            amount = food.fatPer100g,
                            unit = stringResource(id = R.string.grams),
                            amountTextSize = 16.sp,
                            unitTextSize = 12.sp,
                            nameTextStyle = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    }
                }

                AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing.spaceMedium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        OutlinedTextField(
                            value = trackableFoodUiState.amount,
                            onValueChange = {
                                onAmountChange(it)
                            },
                            label = { Text(text = stringResource(id = R.string.grams)) },
                            keyboardOptions = KeyboardOptions(
                                imeAction =
                                if (trackableFoodUiState.amount.isNotBlank()) ImeAction.Done
                                else ImeAction.Default,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onTrack()
                                    defaultKeyboardAction(ImeAction.Done)
                                }
                            ),
                            singleLine = true
                        )
                        
                        IconButton(onClick = onTrack, enabled = trackableFoodUiState.amount.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(id = R.string.track)
                            )
                        }
                    }
                }
            }
        }
    }
}