package com.example.atmos.ui.onboarding.viewmodel
import androidx.lifecycle.ViewModel
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.ui.onboarding.state.OnboardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun onSeeOnboarding() {
        userPreferencesRepository.saveUserPreferences(null)
        userPreferencesRepository.seeOnboarding()
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnSeeOnboarding -> onSeeOnboarding()
        }
    }
}