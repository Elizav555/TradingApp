package com.elizav.tradingapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.tradingapp.domain.interactor.AuthInteractor
import com.elizav.tradingapp.domain.model.AppException.Companion.AUTH_EXCEPTION
import com.elizav.tradingapp.domain.model.Client
import com.elizav.tradingapp.ui.navigation.Destination
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.utils.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val authInteractor: AuthInteractor,
) : ViewModel() {
    private val _commandEvent: Channel<Command> =
        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val commandEvent = _commandEvent.receiveAsFlow()

    init {
        //_commandEvent.trySend(Command.HandleLoadingCommand(isLoading = true))
        //checkAuth()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInEvent -> {
                _commandEvent.trySend(Command.HandleLoadingCommand(isLoading = true))
                // signIn(event.login, event.password)
                signIn(MOCKED_LOGIN, MOCKED_PASSWORD)
            }
        }
    }

    private fun signIn(login: String, password: String) = viewModelScope.launch {
        authInteractor.auth(login, password).fold(
            onSuccess = { navigateToBottomHost(it) },
            onFailure = {
                _commandEvent.trySend(Command.ErrorCommand(it.message ?: AUTH_EXCEPTION))
            }
        )
    }

    private fun checkAuth() = viewModelScope.launch {
        TODO("Not yet implemented")
    }


    private fun navigateToBottomHost(client: Client) {
        appNavigator.tryNavigateTo(Destination.BottomGraphDestination(client = client))
    }

    companion object {
        const val MOCKED_LOGIN = "20234561"
        const val MOCKED_PASSWORD = "ladevi31"
    }
}