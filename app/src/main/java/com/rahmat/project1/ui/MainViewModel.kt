package com.rahmat.project1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.data.local.OnboardingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingManager: OnboardingManager
) : ViewModel() {

    val isOnboardingCompleted: StateFlow<Boolean?> = onboardingManager.isOnboardingCompleted
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null // null artinya sedang mengecek
        )

    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingManager.saveOnboardingCompleted()
        }
    }
}
