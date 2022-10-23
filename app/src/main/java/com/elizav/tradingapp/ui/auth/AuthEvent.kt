package com.elizav.tradingapp.ui.auth

sealed class AuthEvent {
    data class SignInEvent(val login: String, val password: String) : AuthEvent()
}
