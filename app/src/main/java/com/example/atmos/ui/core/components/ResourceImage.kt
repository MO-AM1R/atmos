package com.example.atmos.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.atmos.R
import com.google.android.libraries.places.widget.PlaceSearchFragment


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ResourceImage(
    modifier: Modifier = Modifier,
    resourceId: Int = R.drawable.weather,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        modifier = modifier,
        painter = painterResource(resourceId),
        contentScale = contentScale,
        contentDescription = stringResource(R.string.image)
    )
}