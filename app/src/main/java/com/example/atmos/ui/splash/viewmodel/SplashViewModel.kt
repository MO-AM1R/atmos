package com.example.atmos.ui.splash.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.repository.UserPreferencesRepository
import com.example.atmos.ui.splash.state.SplashUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SplashUIState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            _state.update {
                it.copy(onboardingSeenBefore = userPreferencesRepository.hasSeenOnboarding())
            }
        }
    }
}