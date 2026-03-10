package com.example.atmos.ui.home
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.core.components.ResourceImage

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val backgroundBlurState by rememberSaveable { mutableStateOf(12.dp) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        ResourceImage(
            modifier = Modifier.fillMaxSize().blur(backgroundBlurState),
            resourceId = R.drawable.background,
            contentScale = ContentScale.Crop
        )

        ResourceIcon(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 25.dp)
                .size(120.dp).align(Alignment.TopEnd),
            resourceId = R.drawable.home_icon,
            color = Color(0xFFFFCC33)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {



        }

    }
}


@Composable
fun WeatherCard(){}