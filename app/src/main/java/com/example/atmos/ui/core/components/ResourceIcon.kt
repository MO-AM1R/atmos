package com.example.atmos.ui.core.components
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.atmos.R


@Composable
fun ResourceIcon(
    modifier: Modifier = Modifier,
    resourceId: Int = R.drawable.ic_launcher_foreground,
    color: Color? = null,
) {
    Image(
        modifier = modifier,
        painter = painterResource(resourceId),
        contentDescription = stringResource(R.string.icon),
        colorFilter = if(color != null) ColorFilter.tint(color) else null
    )
}