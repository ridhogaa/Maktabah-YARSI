package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.BuildConfig
import com.maktabah.maktabahyarsi.data.network.api.service.AuthService
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import com.maktabah.maktabahyarsi.data.network.api.service.CategoryService
import com.maktabah.maktabahyarsi.data.network.api.service.SearchService
import com.maktabah.maktabahyarsi.data.network.api.service.UserService
import com.maktabah.maktabahyarsi.data.network.api.service.VisitorCounterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideBookService(retrofit: Retrofit): BookService =
        retrofit.create(BookService::class.java)

    @Provides
    @Singleton
    fun provideVisitorCounterService(retrofit: Retrofit): VisitorCounterService =
        retrofit.create(VisitorCounterService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}