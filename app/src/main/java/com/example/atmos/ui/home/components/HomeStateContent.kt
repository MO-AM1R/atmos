package com.example.atmos.ui.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.atmos.R
import com.example.atmos.ui.core.components.GpsDisabledContent
import com.example.atmos.ui.core.components.LocationPermissionContent
import com.example.atmos.ui.core.components.NetworkUnavailableContent
import com.example.atmos.ui.home.state.HomeScreenState
import com.example.atmos.ui.home.state.HomeUiState


@Composable
fun HomeStateContent(
    uiState: HomeUiState,
    isGpsEnabled: Boolean,
    scrollState: ScrollState,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onRequestPermission: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenGpsSettings: () -> Unit,
) {
    AnimatedContent(
        targetState = uiState.screenState,
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
        },
        label = stringResource(R.string.homestatetransition)
    ) { screenState ->
        if (isGpsEnabled) {
            when (screenState) {
                is HomeScreenState.Loading -> {
                    HomeLoadingContent()
                }

                is HomeScreenState.LocationPermission -> {
                    LocationPermissionContent(
                        onRequestPermission = onRequestPermission,
                        onOpenSettings = onOpenSettings
                    )
                }

                is HomeScreenState.NetworkUnavailable -> {
                    NetworkUnavailableContent()
                }

                is HomeScreenState.Success -> {
                    HomeSuccessContent(
                        uiState = uiState,
                        scrollState = scrollState,
                        onRefresh = onRefresh
                    )
                }

                is HomeScreenState.Error -> {
                    HomeErrorContent(
                        message = screenState.message,
                        onRetry = onRetry
                    )
                }
            }
        }else{
            GpsDisabledContent(
                onOpenGpsSettings = onOpenGpsSettings
            )
        }
    }
}