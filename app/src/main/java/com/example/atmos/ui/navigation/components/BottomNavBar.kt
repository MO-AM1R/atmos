package com.example.atmos.ui.navigation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.atmos.domain.model.BottomNavItem
import com.example.atmos.domain.model.bottomNavItems
import com.example.atmos.ui.navigation.screens.BaseContainerScreens
import com.example.atmos.ui.theme.BottomNavBackground
import com.example.atmos.ui.theme.BottomNavBorder
import com.example.atmos.ui.theme.extraColors


@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination? = null,
    onSelectScreen: (BaseContainerScreens) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(BottomNavBackground)
                .border(color = BottomNavBorder, width = 1.dp, shape = RoundedCornerShape(32.dp))
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentDestination?.hierarchy?.any { dest ->
                    dest.hasRoute(item.screen::class)
                } == true

                FloatingBottomNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onSelectScreen(item.screen) }
                )
            }
        }
    }
}


@Composable
private fun FloatingBottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedBgAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(300),
        label = "bgAlpha"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = animatedBgAlpha * 0.2f)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(item.iconRes),
                contentDescription = stringResource(item.labelRes),
                tint = if (isSelected)
                    extraColors.textPrimary
                else
                    extraColors.textMuted,
                modifier = Modifier.size(22.dp)
            )

            Text(
                text = stringResource(item.labelRes),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = extraColors.textPrimary
            )
        }
    }
}