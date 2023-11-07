package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.service.AuthService
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelper
import com.maktabah.maktabahyarsi.utils.PreferenceDataStoreHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideAuthApiDataSource(authService: AuthService): AuthApiDataSource =
        AuthApiDataSourceImpl(authService)

    @Singleton
    @Provides
    fun provideOnboardingPreferenceDataSource(preferenceDataStoreHelperImpl: PreferenceDataStoreHelperImpl): OnboardingPreferenceDataSource =
        OnboardingPreferenceDataSourceImpl(preferenceDataStoreHelperImpl)
}