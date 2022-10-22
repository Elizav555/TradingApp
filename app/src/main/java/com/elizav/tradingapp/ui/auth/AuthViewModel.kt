package com.elizav.tradingapp.ui.auth

import androidx.lifecycle.ViewModel
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : ViewModel() {
//    private val _commandEvent: Channel<Command> =
//        Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
//    val commandEvent = _commandEvent.receiveAsFlow()
//
//    init {
//        _commandEvent.trySend(Command.HandleLoadingCommand(isLoading = true))
//        checkAuth()
//    }
//
//    fun onEvent(event: AuthEvent) {
//        when (event) {
//            AuthEvent.SignInEvent -> {
//                _commandEvent.trySend(Command.HandleLoadingCommand(isLoading = true))
//                signIn()
//            }
//            is AuthEvent.SignInWithCredEvent -> {
//                _commandEvent.trySend(Command.HandleLoadingCommand(isLoading = true))
//                signInWithCred(event.intentData)
//            }
//        }
//    }

    fun navigateToBottomHost(token: String) {
        appNavigator.tryNavigateTo(Destination.BottomGraphDestination(token = token))
    }
}