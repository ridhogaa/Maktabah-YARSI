package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.data.network.api.service.ContentService
import com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku.ContentViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ContentModule {

    @Provides
    fun provideContentService(retrofit: Retrofit): ContentService =
        retrofit.create(ContentService::class.java)

    @Provides
    fun provideContentViewModel(contentService: ContentService): ContentViewModel =
        ContentViewModel(contentService)
}
