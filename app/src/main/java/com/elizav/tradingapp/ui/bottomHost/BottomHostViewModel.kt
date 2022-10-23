package com.elizav.tradingapp.client.bottomHost

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel
class BottomHostViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _clientState: MutableStateFlow<Client?> =
        MutableStateFlow(null)
    val clientState: StateFlow<Client?> = _clientState

    init {
        val clientLogin: String? = savedStateHandle[Route.BottomGraphRoute.CLIENT_LOGIN]
        val clientPeanutToken: String? =
            savedStateHandle[Route.BottomGraphRoute.CLIENT_PEANUT_TOKEN]
        val clientPartnerToken: String? =
            savedStateHandle[Route.BottomGraphRoute.CLIENT_PARTNER_TOKEN]
        val client: Client? =
            clientLogin?.let {
                Client(
                    login = it,
                    peanutToken = clientPeanutToken,
                    partnerToken = clientPartnerToken
                )
            }
        _clientState.update { client }
    }
}
