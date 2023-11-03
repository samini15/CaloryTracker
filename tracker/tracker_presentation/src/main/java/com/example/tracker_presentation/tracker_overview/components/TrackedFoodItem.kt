package com.example.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.core_ui.LocalSpacing
import com.example.tracker_domain.model.TrackedFood
import com.example.core.R
import com.example.tracker_presentation.components.NutrientInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackedFoodItem(
    modifier: Modifier = Modifier,
    trackedFood: TrackedFood,
    onDeleteClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(spacing.spaceMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 5.dp,
                        bottomStart = 5.dp
                    )
                ),
            painter = rememberImagePainter(
                data = trackedFood.imageUrl,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_dinner)
                    fallback(R.drawable.ic_dinner)
                }
            ),
            contentDescription = trackedFood.name,
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = trackedFood.name,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.width(spacing.spaceMedium))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDeleteClicked() },
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.delete)
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

            Row(verticalAlignment = Alignment.CenterVertically) {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackedFood.carbs,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackedFood.protein,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackedFood.fat,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}