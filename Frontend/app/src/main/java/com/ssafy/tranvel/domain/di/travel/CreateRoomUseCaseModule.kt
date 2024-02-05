package com.ssafy.tranvel.domain.di.travel

import com.ssafy.tranvel.domain.repository.TravelRepository
import com.ssafy.tranvel.domain.usecase.travel.CreateRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CreateRoomUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideCreateUseCase(
        travelRepository: TravelRepository
    ): CreateRoomUseCase = CreateRoomUseCase(travelRepository)
}