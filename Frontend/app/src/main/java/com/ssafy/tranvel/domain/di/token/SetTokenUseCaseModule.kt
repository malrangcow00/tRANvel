package com.ssafy.tranvel.domain.di.token

import com.ssafy.tranvel.domain.repository.TokenRepository
import com.ssafy.tranvel.domain.usecase.token.SetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SetTokenUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideSetTokenUseCase(
        tokenRepository: TokenRepository
    ) = SetTokenUseCase(tokenRepository)
}