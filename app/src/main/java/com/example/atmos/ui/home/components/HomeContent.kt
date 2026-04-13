package com.example.atmos.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.core.components.ResourceImage
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.ui.theme.background
import com.example.atmos.utils.DummyData
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = DummyData.nightUiState,
    onRetry: () -> Unit = {},
    onRequestPermission: () -> Unit = {},
    onOpenSettings: () -> Unit = {},
    onOpenGpsSettings: () -> Unit = {},
    userPreferencesState: UserPreferences? = null,
) {
    val liquidState = rememberLiquidState()

    Box(modifier = modifier.fillMaxSize()) {

        ResourceImage(
            modifier = Modifier
                .fillMaxSize()
                .liquefiable(liquidState),
            resourceId = MaterialTheme.background,
            contentScale = ContentScale.Crop
        )

        HomeStateContent(
            userPreferencesState = userPreferencesState,
            uiState = uiState,
            onRetry = onRetry,
            onRequestPermission = onRequestPermission,
            onOpenSettings = onOpenSettings,
            onOpenGpsSettings = onOpenGpsSettings,
            liquidState = liquidState,
        )
    }
}