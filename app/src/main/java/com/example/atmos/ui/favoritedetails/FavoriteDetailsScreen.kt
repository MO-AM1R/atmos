package com.example.atmos.ui.favoritedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.atmos.domain.model.StoredPoint
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
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState


@Composable
fun FavoriteDetailsScreen(
    point: StoredPoint,
    viewModel: FavoriteDetailsViewModel = hiltViewModel(),
    networkViewModel: NetworkViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val networkState by networkViewModel.isConnected.collectAsStateWithLifecycle()

    LaunchedEffect(point) {
        viewModel.onEvent(FavoriteDetailsEvent.OnLoad(point = point))
    }

    LaunchedEffect(networkState) {
        if (!networkState && !uiState.isDataLoaded) {
            viewModel.setScreenState(FavoriteDetailsScreenState.Error("No internet connection"))
        }
    }

    val liquidState = rememberLiquidState()

    Box {
        ResourceImage(
            modifier = Modifier
                .fillMaxSize()
                .liquefiable(liquidState)
                .blur(4.dp),
            resourceId = background,
            contentScale = ContentScale.Crop
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
                    userPreferencesState = uiState.userPreferences,
                    liquidState = liquidState
                )
            }
        }

        FavoriteDetailsTopBar(
            modifier = Modifier.align(Alignment.TopStart),
            onNavigateBack = onNavigateBack,
                liquidState = liquidState
        )
    }
}