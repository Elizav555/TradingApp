package com.elizav.tradingapp.di

import com.elizav.tradingapp.domain.interactor.auth.AuthInteractor
import com.elizav.tradingapp.domain.interactor.auth.AuthInteractorImpl
import com.elizav.tradingapp.domain.interactor.clientInfo.ClientInfoInteractor
import com.elizav.tradingapp.domain.interactor.clientInfo.ClientInfoInteractorImpl
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractor
import com.elizav.tradingapp.domain.interactor.preferences.PreferencesInteractorImpl
import com.elizav.tradingapp.domain.interactor.promo.PromoInteractor
import com.elizav.tradingapp.domain.interactor.promo.PromoInteractorImpl
import com.elizav.tradingapp.domain.interactor.signals.SignalsInteractor
import com.elizav.tradingapp.domain.interactor.signals.SignalsInteractorImpl
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

    @Binds
    abstract fun bindSignalsInteractor(
        SignalsInteractorImpl: SignalsInteractorImpl
    ): SignalsInteractor

    @Binds
    abstract fun bindPromoInteractor(
        PromoInteractorImpl: PromoInteractorImpl
    ): PromoInteractor

}
