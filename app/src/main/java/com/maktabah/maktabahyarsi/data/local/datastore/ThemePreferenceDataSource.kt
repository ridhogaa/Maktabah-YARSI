package com.maktabah.maktabahyarsi.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface ThemePreferenceDataSource {
    fun getTheme(): Flow<Boolean>
    suspend fun setTheme(condition: Boolean)
}

class ThemePreferenceDataSourceImpl @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : ThemePreferenceDataSource {
    override fun getTheme(): Flow<Boolean> =
        preferenceDataStoreHelper.getPreference(PREF_THEME, false)

    override suspend fun setTheme(condition: Boolean) =
        preferenceDataStoreHelper.putPreference(PREF_THEME, condition)

    companion object {
        val PREF_THEME = booleanPreferencesKey("PREF_THEME")
    }
}