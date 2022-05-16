package com.example.mycomposecookbook.di

import com.example.mycomposecookbook.data.remote.ApiService
import com.example.mycomposecookbook.screen.home.UserRepository
import com.example.mycomposecookbook.screen.home.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService = apiService)
    }
}