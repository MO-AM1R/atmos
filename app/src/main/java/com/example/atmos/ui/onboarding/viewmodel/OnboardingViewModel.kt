package com.example.atmos.ui.onboarding.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.ui.onboarding.state.OnboardingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun onSeeOnboarding() {
        viewModelScope.launch{
            userPreferencesRepository.saveUserPreferences(null)
            userPreferencesRepository.seeOnboarding()
        }
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnSeeOnboarding -> onSeeOnboarding()
        }
    }
}