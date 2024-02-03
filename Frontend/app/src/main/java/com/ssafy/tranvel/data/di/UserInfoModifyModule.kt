package com.ssafy.tranvel.data.di

import androidx.compose.runtime.Stable
import com.ssafy.tranvel.data.remote.api.UserInfoModifyService
import com.ssafy.tranvel.data.remote.datasource.userInfoModification.UserInfoModifyRemoteDataSource
import com.ssafy.tranvel.data.remote.datasource.userInfoModification.impl.UserInfoModifyRemoteDataSourceImpl
import com.ssafy.tranvel.data.repository.UserInfoModifyRepositoryImpl
import com.ssafy.tranvel.domain.repository.UserInfoModifyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Stable
@Module
@InstallIn(SingletonComponent::class)
class UserInfoModifyModule {

    @Provides
    fun provideUserInfoModifyService(retrofit: Retrofit): UserInfoModifyService =
        retrofit.create(UserInfoModifyService::class.java)

    @Provides
    fun provideUserInfoModifyRemoteDataSource(userInfoModifyService: UserInfoModifyService): UserInfoModifyRemoteDataSource =
        UserInfoModifyRemoteDataSourceImpl(userInfoModifyService)

    @Provides
    fun provideUserInfoModifyRepository(userInfoModifyRemoteDataSource: UserInfoModifyRemoteDataSource): UserInfoModifyRepository =
        UserInfoModifyRepositoryImpl(userInfoModifyRemoteDataSource)
}