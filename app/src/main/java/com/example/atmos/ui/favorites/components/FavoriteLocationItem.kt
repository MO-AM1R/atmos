package com.example.atmos.ui.favorites.components

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.domain.model.FavoriteWeatherItem


@Composable
fun FavoriteLocationItem(
    modifier: Modifier = Modifier,
    item: FavoriteWeatherItem,
    temperatureUnit: TemperatureUnit,
    onDelete: () -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { it * 0.4f }
    )

    LaunchedEffect(dismissState.currentValue) {
        if (
            dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd ||
            dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
        ) {
            onDelete()

            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {},
    ) {
        if (item.isLoading) {
            FavoriteLocationShimmer()
        } else {
            FavoriteLocationCard(
                item = item,
                temperatureUnit = temperatureUnit
            )
        }
    }
}