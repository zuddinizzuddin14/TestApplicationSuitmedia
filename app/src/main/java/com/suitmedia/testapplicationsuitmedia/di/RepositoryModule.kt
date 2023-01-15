package com.suitmedia.testapplicationsuitmedia.di

import com.suitmedia.testapplicationsuitmedia.data.network.service.ApiService
import com.suitmedia.testapplicationsuitmedia.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepo(
        apiService: ApiService
    ) : UserRepository = UserRepository(apiService)

}