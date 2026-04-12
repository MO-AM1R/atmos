package com.example.atmos.ui.home.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.core.components.ResourceImage
import com.example.atmos.ui.home.state.HomeUiState
import com.example.atmos.ui.theme.background
import com.example.atmos.ui.theme.homeIcon


@Composable
fun HomeContent(
    uiState: HomeUiState,
    scrollState: ScrollState,
    blurRadius: Dp,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onRequestPermission: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenGpsSettings: () -> Unit,
    modifier: Modifier = Modifier,
    userPreferencesState: UserPreferences?
) {
    Box(modifier = modifier.fillMaxSize()) {

        ResourceImage(
            modifier = Modifier
                .fillMaxSize()
                .blur(blurRadius),
            resourceId = MaterialTheme.background,
            contentScale = ContentScale.Crop
        )

        HomeStateContent(
            userPreferencesState = userPreferencesState,
            uiState = uiState,
            scrollState = scrollState,
            onRetry = onRetry,
            onRefresh = onRefresh,
            onRequestPermission = onRequestPermission,
            onOpenSettings = onOpenSettings,
            onOpenGpsSettings = onOpenGpsSettings
        )
    }
}