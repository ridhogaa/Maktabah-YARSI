package com.maktabah.maktabahyarsi.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.ThemePreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingPreferenceDataSource: OnboardingPreferenceDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource,
) : ViewModel() {

    val getOnboardingPref =
        onboardingPreferenceDataSource.getOnboardingPrefFlow()

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow()


    fun removeSession() = viewModelScope.launch(Dispatchers.IO) {
        userPreferenceDataSource.removeIdPref()
        userPreferenceDataSource.removeTokenPref()
    }

    fun setOnboardingPref(isDone: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        onboardingPreferenceDataSource.setOnboardingPref(isDone)
    }

}