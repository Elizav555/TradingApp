package com.elizav.tradingapp.di

import com.elizav.tradingapp.data.AuthRepositoryImpl
import com.elizav.tradingapp.data.ClientInfoRepositoryImpl
import com.elizav.tradingapp.domain.AuthRepository
import com.elizav.tradingapp.domain.ClientInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        AuthRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindClientInfoRepository(
        ClientInfoRepositoryImpl: ClientInfoRepositoryImpl
    ): ClientInfoRepository
}