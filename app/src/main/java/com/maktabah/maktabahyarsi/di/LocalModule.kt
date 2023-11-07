package com.maktabah.maktabahyarsi.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.maktabah.maktabahyarsi.data.local.datastore.appDataStore
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.appDataStore

    @Singleton
    @Provides
    fun providePreferenceDataStoreHelper(dataStore: DataStore<Preferences>): PreferenceDataStoreHelper =
        PreferenceDataStoreHelperImpl(dataStore)
}