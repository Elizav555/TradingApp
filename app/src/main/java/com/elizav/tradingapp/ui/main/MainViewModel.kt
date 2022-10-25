package com.elizav.tradingapp.ui.main

import androidx.lifecycle.ViewModel
import com.elizav.tradingapp.domain.interactor.auth.AuthInteractor
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val authInteractor: AuthInteractor
) : ViewModel() {
    val authState = authInteractor.isClientAuthed()

    val navigationChannel = appNavigator.navigationChannel

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.onTokenExpired -> {
                appNavigator.tryNavigateTo(Destination.AuthDestination.route)
            }
        }
    }
}