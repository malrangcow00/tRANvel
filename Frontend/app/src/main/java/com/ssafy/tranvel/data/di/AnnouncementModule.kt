package com.ssafy.tranvel.data.di

import androidx.compose.runtime.Stable
import com.ssafy.tranvel.data.remote.api.AnnouncementService
import com.ssafy.tranvel.data.remote.datasource.announcement.AnnouncementRemoteDataSource
import com.ssafy.tranvel.data.remote.datasource.announcement.impl.AnnouncementRemoteDataSourceImpl
import com.ssafy.tranvel.data.repository.AnnouncementRepositoryImpl
import com.ssafy.tranvel.domain.repository.AnnouncementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Stable
@Module
@InstallIn(ViewModelComponent::class)
class AnnouncementModule {

    @Provides
    fun provideAnnouncementService(retrofit: Retrofit): AnnouncementService = retrofit.create(AnnouncementService::class.java)

    @Provides
    fun provideAnnouncementRemoteDataSource(
        announcementService: AnnouncementService,
    ): AnnouncementRemoteDataSource =
        AnnouncementRemoteDataSourceImpl(announcementService)

    @Provides
    fun provideCharacterRepository(
        accountRemoteDataSource: AnnouncementRemoteDataSource,
    ): AnnouncementRepository =
        AnnouncementRepositoryImpl(accountRemoteDataSource)
}
