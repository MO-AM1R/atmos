package com.example.atmos.ui.splash.viewmodel
import androidx.lifecycle.ViewModel
import com.example.atmos.domain.userpreferences.UserPreferencesRepository
import com.example.atmos.ui.splash.state.SplashUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        SplashUIState(
            onboardingSeenBefore =
                userPreferencesRepository.isOnboardingSeenBefore()
        )
    )

    val state = _state.asStateFlow()
}