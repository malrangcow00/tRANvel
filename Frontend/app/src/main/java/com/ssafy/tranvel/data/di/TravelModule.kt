package com.ssafy.tranvel.data.di

import com.ssafy.tranvel.data.remote.api.TravelService
import com.ssafy.tranvel.data.remote.datasource.travel.TravelDataSource
import com.ssafy.tranvel.data.remote.datasource.travel.impl.TravelDataSourceImpl
import com.ssafy.tranvel.data.repository.TravelRepositoryImpl
import com.ssafy.tranvel.domain.repository.TravelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TravelModule {

    @Provides
    @Singleton
    fun provideTravelService(
        retrofit: Retrofit
    ): TravelService = retrofit.create(TravelService::class.java)

    @Provides
    @Singleton
    fun provideTravelDataSource(
        travelService: TravelService
    ): TravelDataSource = TravelDataSourceImpl(travelService)

    @Provides
    @Singleton
    fun provideTravelRepository(
        travelDataSource: TravelDataSource
    ): TravelRepository = TravelRepositoryImpl(travelDataSource)
}