package com.elizav.tradingapp.ui.signals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.signals.SignalsInteractor
import com.elizav.tradingapp.domain.model.utils.AppException.Companion.API_EXCEPTION
import com.elizav.tradingapp.domain.model.signal.Pair
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.signals.state.SignalsListEvent
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState
import com.elizav.tradingapp.domain.model.utils.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignalsListViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val signalsInteractor: SignalsInteractor
) : ViewModel() {
    private val _commandEvent: Channel<Command> =
        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val commandEvent = _commandEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<SignalsListScreenState> =
        MutableStateFlow(SignalsListScreenState(true))
    val uiState: StateFlow<SignalsListScreenState> = _uiState

    fun onEvent(event: SignalsListEvent) {
        when (event) {
            is SignalsListEvent.GetSignalsList -> {
                _uiState.update { it.copy(isLoading = true) }
                getSignals(event.pairs, event.from, event.to)
            }
            is SignalsListEvent.InitClientEvent -> {
                _uiState.update { it.copy(isLoading = false, client = event.client) }
            }
        }
    }

    private fun getSignals(pairs: List<Pair>, from: Calendar, to: Calendar) =
        viewModelScope.launch {
            _uiState.value.client?.let { client ->
                signalsInteractor.getSignals(client = client, pairs = pairs, from = from, to = to)
                    .fold(
                        onSuccess = { signals ->
                            _uiState.update { it.copy(isLoading = false, signals = signals) }
                        },
                        onFailure = { ex ->
                            _commandEvent.trySend(
                                Command.ErrorCommand(
                                    ex.message ?: API_EXCEPTION
                                )
                            )
                        }
                    )
            }
        }
}