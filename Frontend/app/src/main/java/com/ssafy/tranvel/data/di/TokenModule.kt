package com.ssafy.tranvel.data.di

import com.ssafy.tranvel.data.local.PreferenceDataSource
import com.ssafy.tranvel.data.repository.TokenRepositoryImpl
import com.ssafy.tranvel.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {

    @Provides
    @Singleton
    fun provideTokenRepository(
        preferenceDataSource: PreferenceDataSource
    ): TokenRepository = TokenRepositoryImpl(preferenceDataSource)
}