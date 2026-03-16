package com.example.atmos.ui.favoritedetails

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.core.components.ResourceImage
import com.example.atmos.ui.core.viewmodel.NetworkViewModel
import com.example.atmos.ui.favoritedetails.components.FavoriteDetailsErrorContent
import com.example.atmos.ui.favoritedetails.components.FavoriteDetailsLoadingContent
import com.example.atmos.ui.favoritedetails.components.FavoriteDetailsSuccessContent
import com.example.atmos.ui.favoritedetails.components.FavoriteDetailsTopBar
import com.example.atmos.ui.favoritedetails.state.FavoriteDetailsEvent
import com.example.atmos.ui.favoritedetails.state.FavoriteDetailsScreenState
import com.example.atmos.ui.favoritedetails.viewmodel.FavoriteDetailsViewModel
import com.example.atmos.ui.theme.Padding
import com.example.atmos.ui.theme.background
import com.example.atmos.ui.theme.homeIcon


@Composable
fun FavoriteDetailsScreen(
    modifier: Modifier = Modifier,
    point: StoredPoint,
    viewModel: FavoriteDetailsViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val networkState by networkViewModel.isConnected.collectAsStateWithLifecycle()

    val isScrolledPastThreshold by remember {
        derivedStateOf { scrollState.value > 100 }
    }

    val blurRadius by animateDpAsState(
        targetValue = if (isScrolledPastThreshold) 0.dp else 12.dp,
        animationSpec = tween(durationMillis = 400),
        label = "blurRadius"
    )

    LaunchedEffect(point) {
        viewModel.onEvent(FavoriteDetailsEvent.OnLoad(point = point))
    }

    LaunchedEffect(networkState) {
        if (!networkState && !uiState.isDataLoaded) {
            viewModel.setScreenState(FavoriteDetailsScreenState.Error("No internet connection"))
        }
    }

    Box {
        ResourceImage(
            modifier = Modifier
                .fillMaxSize()
                .blur(blurRadius),
            resourceId = MaterialTheme.background,
            contentScale = ContentScale.Crop
        )

        ResourceIcon(
            Modifier
                .padding(horizontal = 25.dp, vertical = 50.dp)
                .size(48.dp)
                .align(Alignment.TopEnd)
                .blur(blurRadius), MaterialTheme.homeIcon
        )

        when (uiState.screenState) {
            FavoriteDetailsScreenState.Loading -> {
                FavoriteDetailsLoadingContent()
            }

            is FavoriteDetailsScreenState.Error -> {
                FavoriteDetailsErrorContent(
                    modifier = Modifier.padding(
                        horizontal = Padding.screenPadding.first,
                        vertical = Padding.screenPadding.second
                    ),
                    message = (uiState.screenState as FavoriteDetailsScreenState.Error).message,
                    onRetry = {
                        viewModel.onEvent(
                            FavoriteDetailsEvent.OnLoad(
                                point = point,
                                forceUpdate = true
                            )
                        )
                    }
                )
            }

            FavoriteDetailsScreenState.Success -> {
                FavoriteDetailsSuccessContent(
                    modifier = Modifier.padding(
                        horizontal = Padding.screenPadding.first,
                        vertical = Padding.screenPadding.second
                    ),
                    uiState = uiState,
                    scrollState = scrollState,
                    blurRadius = blurRadius,
                    userPreferencesState = uiState.userPreferences,
                    onRefresh = {
                        viewModel.onEvent(
                            FavoriteDetailsEvent.OnLoad(
                                point = point,
                                forceUpdate = true
                            )
                        )
                    }
                )
            }
        }

        FavoriteDetailsTopBar(
            modifier = Modifier.align(Alignment.TopStart),
            onNavigateBack = onNavigateBack
        )
    }
}