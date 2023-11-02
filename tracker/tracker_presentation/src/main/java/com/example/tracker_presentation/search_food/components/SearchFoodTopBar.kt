package com.example.tracker_presentation.search_food.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFoodTopBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onActiveChanged: (Boolean) -> Unit,
    onSearchClick: () -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = {onQueryChanged(it)},
        onSearch = {onSearchClick()},
        active = false,
        onActiveChange = {
            onActiveChanged(it)
        },
        placeholder = {
            Text(text = "Search Foods")
        },
        leadingIcon = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(id = R.string.search))
            }
        },
        tonalElevation = 20.dp
    ) {

    }
}

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchFoodTopBar(
        query = "",
        onQueryChanged = {},
        onSearchClick = {},
        onActiveChanged = {})
}