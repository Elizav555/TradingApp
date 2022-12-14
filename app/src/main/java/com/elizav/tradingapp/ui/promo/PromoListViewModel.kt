package com.elizav.tradingapp.ui.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.promo.PromoInteractor
import com.elizav.tradingapp.domain.utils.AppException.Companion.CLIENT_STATE_EXCEPTION
import com.elizav.tradingapp.domain.utils.EncodedLink
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.promo.state.PromoListEvent
import com.elizav.tradingapp.ui.promo.state.PromoListScreenState
import com.elizav.tradingapp.domain.utils.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PromoListViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val promoInteractor: PromoInteractor
) : ViewModel() {
    private val _commandEvent: Channel<Command> =
        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val commandEvent = _commandEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<PromoListScreenState> =
        MutableStateFlow(PromoListScreenState(true))
    val uiState: StateFlow<PromoListScreenState> = _uiState

    init {
        loadPromo()
    }

    fun onEvent(event: PromoListEvent) {
        when (event) {
            is PromoListEvent.OnPromoClick -> {
                appNavigator.tryNavigateTo(Route.WebViewRoute().invoke(EncodedLink(event.link).encodedLink))
            }
        }
    }

    private fun loadPromo() = viewModelScope.launch {
        promoInteractor.getPromo().fold(
            onSuccess = { promoList ->
                _uiState.update { it.copy(isLoading = false, promoList = promoList) }
            },
            onFailure = { ex ->
                _commandEvent.trySend(
                    Command.ErrorCommand(
                        ex.message ?: CLIENT_STATE_EXCEPTION
                    )
                )
            }
        )
    }
}