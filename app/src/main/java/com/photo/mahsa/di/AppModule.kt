package com.photo.mahsa.di

import com.photo.mahsa.data.Repository
import com.photo.mahsa.data.RepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun provideRepository(repositoryImp: RepositoryImp): Repository
}