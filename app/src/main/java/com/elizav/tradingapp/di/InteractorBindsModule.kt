package com.elizav.tradingapp.di

import com.elizav.tradingapp.domain.interactor.AuthInteractor
import com.elizav.tradingapp.domain.interactor.AuthInteractorImpl
import com.elizav.tradingapp.domain.interactor.ClientInfoInteractor
import com.elizav.tradingapp.domain.interactor.ClientInfoInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorBindsModule {

    @Binds
    abstract fun bindAuthInteractor(
        AuthInteractorImpl: AuthInteractorImpl
    ): AuthInteractor

    @Binds
    abstract fun bindClientInfoInteractor(
        ClientInfoInteractorImpl: ClientInfoInteractorImpl
    ): ClientInfoInteractor
}
