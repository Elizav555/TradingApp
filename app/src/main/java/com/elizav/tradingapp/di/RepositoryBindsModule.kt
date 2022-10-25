package com.elizav.tradingapp.di

import com.elizav.tradingapp.data.preferences.PreferencesImpl
import com.elizav.tradingapp.data.repository.AuthRepositoryImpl
import com.elizav.tradingapp.data.repository.ClientInfoRepositoryImpl
import com.elizav.tradingapp.data.repository.PreferencesRepositoryImpl
import com.elizav.tradingapp.data.repository.PromoRepositoryImpl
import com.elizav.tradingapp.data.repository.SignalsRepositoryImpl
import com.elizav.tradingapp.domain.preferences.Preferences
import com.elizav.tradingapp.domain.repository.AuthRepository
import com.elizav.tradingapp.domain.repository.ClientInfoRepository
import com.elizav.tradingapp.domain.repository.PreferencesRepository
import com.elizav.tradingapp.domain.repository.PromoRepository
import com.elizav.tradingapp.domain.repository.SignalsRepository
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

    @Binds
    @Singleton
    abstract fun bindSignalsRepository(
        SignalsRepositoryImpl: SignalsRepositoryImpl
    ): SignalsRepository

    @Binds
    @Singleton
    abstract fun bindPromoRepository(
        PromoRepositoryImpl: PromoRepositoryImpl
    ): PromoRepository


}