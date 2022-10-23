package com.elizav.tradingapp.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.AuthInteractor
import com.elizav.tradingapp.domain.interactor.ClientInfoInteractor
import com.elizav.tradingapp.domain.model.AppException.Companion.CLIENT_STATE_EXCEPTION
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.profile.state.ProfileEvent
import com.elizav.tradingapp.ui.profile.state.ProfileScreenState
import com.elizav.tradingapp.ui.utils.Command
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
class ProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
    private val authInteractor: AuthInteractor,
    private val clientInfoInteractor: ClientInfoInteractor,
) : ViewModel() {
    private val _commandEvent: Channel<Command> =
        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val commandEvent = _commandEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<ProfileScreenState> =
        MutableStateFlow(ProfileScreenState(true))
    val uiState: StateFlow<ProfileScreenState> = _uiState

    init {
        val client: Client? =
            savedStateHandle.get<Client>(Route.BottomGraphRoute.CLIENT_KEY)
        _uiState.update { it.copy(isLoading = true, client = client) }
        client?.let { loadInfo(it) }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LogoutEvent -> {
                _uiState.update { it.copy(isLoading = true) }
                logout()
            }
        }
    }

    private fun logout() = viewModelScope.launch {
        authInteractor.logout()
    }

    private fun loadInfo(client: Client) = viewModelScope.launch {
        clientInfoInteractor.getClientInfo(client).fold(
            onSuccess = { clientInfo ->
                _uiState.update { it.copy(isLoading = false, clientInfo = clientInfo) }
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