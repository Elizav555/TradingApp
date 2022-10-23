package com.elizav.tradingapp.domain.model

sealed class AppException(override val message: String) : Exception(message) {
    data class ApiException(override val message: String = API_EXCEPTION) : AppException(message)
    data class AuthException(override val message: String = AUTH_EXCEPTION) : AppException(message)
    data class ClientStateException(override val message: String = CLIENT_STATE_EXCEPTION) :
        AppException(message)

    companion object {
        const val API_EXCEPTION = "Api Error"
        const val AUTH_EXCEPTION = "Auth Error"
        const val CLIENT_STATE_EXCEPTION = "Illegal Client state"
    }
}
