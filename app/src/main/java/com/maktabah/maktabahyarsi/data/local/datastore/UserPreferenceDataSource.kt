package com.maktabah.maktabahyarsi.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface UserPreferenceDataSource {
    fun getUserTokenPrefFlow(): Flow<String>
    suspend fun setUserTokenPref(token: String)
    fun getUserIdPrefFlow(): Flow<String>
    suspend fun setUserIdPref(idUser: String)

    suspend fun removeTokenPref()

    suspend fun removeIdPref()
}

class UserPreferenceDataSourceImpl @Inject constructor(
    private val preferenceDataStoreHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {
    override fun getUserTokenPrefFlow(): Flow<String> =
        preferenceDataStoreHelper.getPreference(PREF_USER_TOKEN, "")

    override suspend fun setUserTokenPref(token: String) =
        preferenceDataStoreHelper.putPreference(PREF_USER_TOKEN, token)

    override fun getUserIdPrefFlow(): Flow<String> =
        preferenceDataStoreHelper.getPreference(PREF_USER_ID, "")

    override suspend fun setUserIdPref(idUser: String) =
        preferenceDataStoreHelper.putPreference(PREF_USER_ID, idUser)

    override suspend fun removeTokenPref() =
        preferenceDataStoreHelper.removePreference(PREF_USER_TOKEN)

    override suspend fun removeIdPref() =
        preferenceDataStoreHelper.removePreference(PREF_USER_ID)

    companion object {
        val PREF_USER_TOKEN = stringPreferencesKey("PREF_USER_TOKEN")
        val PREF_USER_ID = stringPreferencesKey("PREF_USER_ID")
    }
}

