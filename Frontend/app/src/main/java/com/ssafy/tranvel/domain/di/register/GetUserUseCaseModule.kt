package com.ssafy.tranvel.domain.di.register

import com.ssafy.tranvel.domain.repository.RegisterRepository
import com.ssafy.tranvel.domain.usecase.register.GetUserUseCase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
class GetUserUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserUseCase(
        registerRepository: RegisterRepository
    ) = GetUserUseCase(registerRepository)
}