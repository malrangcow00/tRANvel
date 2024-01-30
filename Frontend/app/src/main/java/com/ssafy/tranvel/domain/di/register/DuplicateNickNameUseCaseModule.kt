package com.ssafy.tranvel.domain.di.register

import com.ssafy.tranvel.domain.repository.RegisterRepository
import com.ssafy.tranvel.domain.usecase.register.DuplicateNickNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DuplicateNickNameUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideDuplicateNickNameUseCase(
        registerRepository: RegisterRepository
    ) = DuplicateNickNameUseCase(registerRepository)
}