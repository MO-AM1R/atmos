package com.example.atmos.ui.onboarding.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.atmos.domain.onboarding.OnboardingItem

@Composable
fun OnboardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pages: List<OnboardingItem>
) {
    HorizontalPager(
        state    = pagerState,
        modifier = modifier.fillMaxWidth(),
        key      = { index -> pages[index].title }
    ) { pageIndex ->
        OnboardingPage(
            item          = pages[pageIndex],
            currentPage   = pagerState.currentPage,
            pageIndex     = pageIndex,
            modifier      = Modifier.fillMaxWidth()
        )
    }
}