package com.example.atmos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.atmos.R


@Composable
fun ResourceImage(
    modifier: Modifier = Modifier,
    resourceId: Int = R.drawable.ic_launcher_foreground
) {
    Image(
        modifier = modifier,
        painter = painterResource(resourceId),
        contentDescription = stringResource(R.string.image)
    )
}