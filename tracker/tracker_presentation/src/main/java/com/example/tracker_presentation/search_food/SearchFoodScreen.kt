package com.example.tracker_presentation.search_food

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.tracker_presentation.search_food.components.SearchFoodTopBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchFoodScreen(
    snakbarState: SnackbarHostState,
    onNavigateUp: () -> Unit,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    viewModel: SearchFoodViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                is UiEvent.ShowSnakbar -> {
                    snakbarState.showSnackbar(message = event.message.asString(context))
                    keyboardController?.hide()
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.add_meal, mealName),
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        SearchFoodTopBar(
            query = state.searchQuery,
            onQueryChanged = {
                viewModel.onEvent(SearchFoodEvent.OnQueryChanged(it))
            },
            onActiveChanged = {
                viewModel.onEvent(SearchFoodEvent.OnSearchActiveChanged(it))
            }
        ) {
            viewModel.onEvent(SearchFoodEvent.OnSearch)
        }
    }
}