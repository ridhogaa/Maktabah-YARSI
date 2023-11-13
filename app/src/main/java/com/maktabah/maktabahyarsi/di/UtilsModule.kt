package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.utils.JwtUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    @Singleton
    fun provideJwtUtils(): JwtUtils =
        JwtUtils()
}