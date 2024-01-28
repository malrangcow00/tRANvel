package com.ssafy.tranvel.data.di

import com.ssafy.tranvel.data.remote.api.RegisterService
import com.ssafy.tranvel.data.remote.datasource.register.RegisterDataSource
import com.ssafy.tranvel.data.remote.datasource.register.impl.RegisterDataSourceImpl
import com.ssafy.tranvel.data.repository.RegisterRepositoryImpl
import com.ssafy.tranvel.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterService =
        retrofit.create(RegisterService::class.java)

    @Provides
    fun provideRegisterDatasource(
        registerService: RegisterService
    ): RegisterDataSource = RegisterDataSourceImpl(registerService)

    @Provides
    fun provideRegisterRepository(
        registerDataSource: RegisterDataSource
    ): RegisterRepository = RegisterRepositoryImpl(registerDataSource)
}