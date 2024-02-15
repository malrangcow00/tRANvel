package com.ssafy.tranvel.data.di

import androidx.compose.runtime.Stable
import com.ssafy.tranvel.data.remote.api.HistoryService
import com.ssafy.tranvel.data.remote.datasource.history.HistoryRemoteDataSource
import com.ssafy.tranvel.data.remote.datasource.history.impl.HistoryRemoteDataSourceImpl
import com.ssafy.tranvel.data.repository.HistoryRepositoryImpl
import com.ssafy.tranvel.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class HistoryModule {
    @Provides
    fun provideHistoryService(retrofit: Retrofit) : HistoryService = retrofit.create(HistoryService::class.java)

    @Provides
    fun provideHistoryRemoteDataSource(
        historyService: HistoryService
    ): HistoryRemoteDataSource = HistoryRemoteDataSourceImpl(historyService)

    @Provides
    fun provideHistoryRepository(
        historyRemoteDataSource: HistoryRemoteDataSource
    ):HistoryRepository = HistoryRepositoryImpl(historyRemoteDataSource)
}
