package com.elizav.tradingapp.ui.signals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.signals.SignalsInteractor
import com.elizav.tradingapp.domain.model.signal.SignalPair
import com.elizav.tradingapp.domain.utils.AppException.Companion.API_EXCEPTION
import com.elizav.tradingapp.domain.utils.AppException.Companion.SIGNALS_PARAMS_EXCEPTION
import com.elizav.tradingapp.domain.utils.Command
import com.elizav.tradingapp.domain.utils.DateParser.getTimeInUnixDateFromString
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.signals.state.SignalsListEvent
import com.elizav.tradingapp.ui.signals.state.SignalsListScreenState
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
                submitDialog()
            }
            is SignalsListEvent.InitClientEvent -> {
                _uiState.update { it.copy(isLoading = false, client = event.client) }
            }
            is SignalsListEvent.UpdateFromString -> {
                updateFromString(event.newValue)
            }
            is SignalsListEvent.UpdatePairs -> {
                updatePairs(event.isChecked, event.signalPair)
            }
            is SignalsListEvent.UpdateToString -> {
                updateToString(event.newValue)
            }
            is SignalsListEvent.HandleDialog -> {
                _uiState.update { it.copy(isDialogOpened = event.isOpened) }
            }
        }
    }

    private fun updateFromString(newValue: String) {
        val validated = if (_uiState.value.fromString.length > newValue.length) {
            newValue
        } else validateDateString(newValue)
        validated?.let { _uiState.update { it.copy(fromString = validated) } }
    }

    private fun updateToString(newValue: String) {
        val validated = if (_uiState.value.toString.length > newValue.length) {
            newValue
        } else validateDateString(newValue)
        validated?.let {_uiState.update { it.copy(toString = validated) } }
    }

    private fun validateDateString(dateString: String): String? {
        val newChar = dateString.toCharArray().last()
        if (!newChar.isDigit()) {
            return null
        }
        return when (dateString.length) {
            2 -> "$dateString/"
            5 -> "$dateString/"
            in 11..Int.MAX_VALUE -> null
            else -> dateString
        }
    }

    private fun submitDialog() {
        val from = getTimeInUnixDateFromString(_uiState.value.fromString)
        val to = getTimeInUnixDateFromString(_uiState.value.toString)
        if (from == null || to == null || to < from || _uiState.value.signalPairs.isEmpty()) {
            _commandEvent.trySend(
                Command.ErrorCommand(
                    SIGNALS_PARAMS_EXCEPTION
                )
            )
        } else getSignals(to, from)
    }

    private fun updatePairs(checked: Boolean, signalPair: SignalPair) {
        val newPairs = _uiState.value.signalPairs.toMutableSet()
        if (checked) {
            newPairs.add(signalPair)
        } else {
            newPairs.remove(signalPair)
        }
        _uiState.update { it.copy(signalPairs = newPairs) }
    }

    private fun getSignals(to: Long, from: Long) =
        viewModelScope.launch {
            _uiState.value.client?.let { client ->
                signalsInteractor.getSignals(
                    client = client,
                    signalPairs = _uiState.value.signalPairs.toList(),
                    from = from,
                    to = to
                )
                    .fold(
                        onSuccess = { signals ->
                            _uiState.update { it.copy(isLoading = false, signals = signals, isDialogOpened = false) }
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