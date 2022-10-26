package com.elizav.tradingapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.auth.AuthInteractor
import com.elizav.tradingapp.domain.model.client.Client
import com.elizav.tradingapp.domain.utils.AppException.Companion.AUTH_EXCEPTION
import com.elizav.tradingapp.domain.utils.Command
import com.elizav.tradingapp.ui.auth.state.AuthEvent
import com.elizav.tradingapp.ui.auth.state.AuthScreenState
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
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
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val authInteractor: AuthInteractor,
) : ViewModel() {
    private val _commandEvent: Channel<Command> =
        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val commandEvent = _commandEvent.receiveAsFlow()

    private val _uiState: MutableStateFlow<AuthScreenState> =
        MutableStateFlow(AuthScreenState(true))
    val uiState: StateFlow<AuthScreenState> = _uiState

    init {
        checkAuth()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInEvent -> {
                _uiState.update { it.copy(isLoading = true) }
                // signIn(event.login, event.password)
                //Mocked values
                signIn(MOCKED_LOGIN, MOCKED_PASSWORD)
            }
        }
    }

    private fun signIn(login: String, password: String) = viewModelScope.launch {
        authInteractor.auth(login, password).fold(
            onSuccess = { client ->
                navigateToBottomHost(client)
            },
            onFailure = {
                _commandEvent.trySend(Command.ErrorCommand(it.message ?: AUTH_EXCEPTION))
            }
        )
    }

    private fun checkAuth() = viewModelScope.launch {
        authInteractor.checkAuth().fold(
            onSuccess = { client ->
                client?.let {
                    navigateToBottomHost(client)
                } ?: _uiState.update { it.copy(isLoading = false) }
            },
            onFailure = { ex ->
                _commandEvent.trySend(Command.ErrorCommand(ex.message ?: AUTH_EXCEPTION))
                _uiState.update { it.copy(isLoading = false) }
            }
        )
    }

    private fun navigateToBottomHost(client: Client) {
        appNavigator.tryNavigateTo(Route.BottomGraphRoute().invoke(client = client))
    }

    companion object {
        const val MOCKED_LOGIN = "20234561"
        const val MOCKED_PASSWORD = "ladevi31"
    }
}