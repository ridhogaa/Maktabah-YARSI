package com.maktabah.maktabahyarsi.ui.splashscreen

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
class SplashScreenViewModel @Inject constructor(
    private val onboardingPreferenceDataSource: OnboardingPreferenceDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource,
) : ViewModel() {
    val getOnboardingPref =
        onboardingPreferenceDataSource.getOnboardingPrefFlow().asLiveData(Dispatchers.IO)

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)

    fun removeSession() = viewModelScope.launch(Dispatchers.IO) {
        userPreferenceDataSource.removeIdPref()
        userPreferenceDataSource.removeTokenPref()
    }
}