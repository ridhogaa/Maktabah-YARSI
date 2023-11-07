package com.maktabah.maktabahyarsi.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingPreferenceDataSource: OnboardingPreferenceDataSource
) : ViewModel() {

    val getOnboardingPref = onboardingPreferenceDataSource.getOnboardingPrefFlow().asLiveData(Dispatchers.IO)
    fun setOnboardingPref(isDone: Boolean) = viewModelScope.launch {
        onboardingPreferenceDataSource.setOnboardingPref(isDone)
    }

}