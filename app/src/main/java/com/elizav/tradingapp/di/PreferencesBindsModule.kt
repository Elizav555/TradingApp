package com.elizav.tradingapp.di

import com.elizav.tradingapp.data.preferences.PreferencesImpl
import com.elizav.tradingapp.data.repository.PreferencesRepositoryImpl
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractor
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractorImpl
import com.elizav.tradingapp.domain.preferences.Preferences
import com.elizav.tradingapp.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesBindsModule {
    @Binds
    abstract fun bindPreferencesInteractor(
        PreferencesInteractorImpl: PreferencesInteractorImpl
    ): PreferencesInteractor

    @Binds
    @Singleton
    abstract fun bindPreferences(
        PreferencesImpl: PreferencesImpl
    ): Preferences

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        PreferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}