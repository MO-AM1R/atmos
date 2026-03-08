package com.example.atmos.ui.core.components
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MyPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState = PagerState { 4 },
    activeDotColor: Color = Color.Blue,
    dotColor: Color = Color.Gray,
    pageCount: Int = 4,
    activeDotSize: Dp = 8.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (isSelected) activeDotSize else 12.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) activeDotColor else dotColor)
            )
        }
    }
}