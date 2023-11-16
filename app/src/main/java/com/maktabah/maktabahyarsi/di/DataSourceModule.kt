package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.OnboardingPreferenceDataSourceImpl
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.CategoryApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.CategoryApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.UserApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.UserApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.datasource.VisitorCounterApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.VisitorCounterApiDataSourceImpl
import com.maktabah.maktabahyarsi.data.network.api.service.AuthService
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import com.maktabah.maktabahyarsi.data.network.api.service.CategoryService
import com.maktabah.maktabahyarsi.data.network.api.service.UserService
import com.maktabah.maktabahyarsi.data.network.api.service.VisitorCounterService
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
    fun provideUserApiDataSource(userService: UserService): UserApiDataSource =
        UserApiDataSourceImpl(userService)

    @Singleton
    @Provides
    fun provideCategoryApiDataSource(categoryService: CategoryService): CategoryApiDataSource =
        CategoryApiDataSourceImpl(categoryService)

    @Singleton
    @Provides
    fun provideBookApiDataSource(bookService: BookService): BookApiDataSource =
        BookApiDataSourceImpl(bookService)

    @Singleton
    @Provides
    fun provideVisitorCounterApiDataSource(visitorCounterService: VisitorCounterService): VisitorCounterApiDataSource =
        VisitorCounterApiDataSourceImpl(visitorCounterService)

    @Singleton
    @Provides
    fun provideOnboardingPreferenceDataSource(preferenceDataStoreHelperImpl: PreferenceDataStoreHelperImpl): OnboardingPreferenceDataSource =
        OnboardingPreferenceDataSourceImpl(preferenceDataStoreHelperImpl)

    @Singleton
    @Provides
    fun provideUserPreferenceDataSource(preferenceDataStoreHelperImpl: PreferenceDataStoreHelperImpl): UserPreferenceDataSource =
        UserPreferenceDataSourceImpl(preferenceDataStoreHelperImpl)

}