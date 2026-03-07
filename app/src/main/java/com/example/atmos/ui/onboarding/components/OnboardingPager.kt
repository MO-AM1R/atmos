package com.example.atmos.ui.onboarding.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.atmos.domain.onboarding.OnboardingItem

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun OnboardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState = PagerState { 4 },
    pages: List<OnboardingItem> = listOf()
) {
    HorizontalPager(
        state    = pagerState,
        modifier = modifier.fillMaxWidth(),
        key      = { index -> pages[index].title },
        userScrollEnabled = false,
    ) { pageIndex ->
        OnboardingPage(
            item          = pages[pageIndex],
            currentPage   = pagerState.currentPage,
            pageIndex     = pageIndex,
            modifier      = Modifier.fillMaxWidth()
        )
    }
}