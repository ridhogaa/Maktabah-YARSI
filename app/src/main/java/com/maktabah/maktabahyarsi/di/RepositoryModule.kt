package com.maktabah.maktabahyarsi.di

import com.maktabah.maktabahyarsi.data.repository.AuthRepository
import com.maktabah.maktabahyarsi.data.repository.AuthRepositoryImpl
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.data.repository.BookRepositoryImpl
import com.maktabah.maktabahyarsi.data.repository.CategoryRepository
import com.maktabah.maktabahyarsi.data.repository.CategoryRepositoryImpl
import com.maktabah.maktabahyarsi.data.repository.UserRepository
import com.maktabah.maktabahyarsi.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun provideBookRepository(bookRepositoryImpl: BookRepositoryImpl): BookRepository

}