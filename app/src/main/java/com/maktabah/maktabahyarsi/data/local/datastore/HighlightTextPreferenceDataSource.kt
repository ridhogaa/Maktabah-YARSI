package com.maktabah.maktabahyarsi.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface HighlightTextPreferenceDataSource {
    fun getHighlightTextPrefFlow(): Flow<String>
    suspend fun setHighlightTextPref(text: String)
    suspend fun removeHighlightTextPref()
}

class HighlightTextPreferenceDataSourceImpl @Inject constructor(
    private val helper: PreferenceDataStoreHelper
) : HighlightTextPreferenceDataSource {
    override fun getHighlightTextPrefFlow(): Flow<String> =
        helper.getPreference(PREF_HIGHLIGHT_TEXT, "")

    override suspend fun setHighlightTextPref(text: String) =
        helper.putPreference(PREF_HIGHLIGHT_TEXT, text)

    override suspend fun removeHighlightTextPref() =
        helper.removePreference(PREF_HIGHLIGHT_TEXT)

    companion object {
        val PREF_HIGHLIGHT_TEXT = stringPreferencesKey("PREF_HIGHLIGHT_TEXT")
    }
}