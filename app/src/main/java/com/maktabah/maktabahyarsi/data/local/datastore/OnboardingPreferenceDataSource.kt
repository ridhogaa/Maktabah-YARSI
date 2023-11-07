package com.maktabah.maktabahyarsi.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface OnboardingPreferenceDataSource {
    fun getOnboardingPrefFlow(): Flow<Boolean>
    suspend fun setOnboardingPref(isDone: Boolean)
}

class OnboardingPreferenceDataSourceImpl @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : OnboardingPreferenceDataSource {
    override fun getOnboardingPrefFlow(): Flow<Boolean> =
        preferenceDataStoreHelper.getPreference(PREF_ONBOARDING, false)

    override suspend fun setOnboardingPref(isDone: Boolean) =
        preferenceDataStoreHelper.putPreference(
            PREF_ONBOARDING, isDone
        )

    companion object {
        val PREF_ONBOARDING = booleanPreferencesKey("PREF_ONBOARDING")
    }
}