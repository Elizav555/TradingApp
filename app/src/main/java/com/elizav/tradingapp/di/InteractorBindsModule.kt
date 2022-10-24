package com.elizav.tradingapp.di

import com.elizav.tradingapp.domain.interactor.AuthInteractor
import com.elizav.tradingapp.domain.interactor.AuthInteractorImpl
import com.elizav.tradingapp.domain.interactor.ClientInfoInteractor
import com.elizav.tradingapp.domain.interactor.ClientInfoInteractorImpl
import com.elizav.tradingapp.domain.interactor.PromoInteractor
import com.elizav.tradingapp.domain.interactor.SignalsInteractor
import com.elizav.tradingapp.domain.interactor.SignalsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.elizav.tradingapp.domain.interactor.PromoInteractorImpl as PromoInteractorImpl1

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

    @Binds
    abstract fun bindSignalsInteractor(
        SignalsInteractorImpl: SignalsInteractorImpl
    ): SignalsInteractor

    @Binds
    abstract fun bindPromoInteractor(
        PromoInteractorImpl: PromoInteractorImpl1
    ): PromoInteractor
}
