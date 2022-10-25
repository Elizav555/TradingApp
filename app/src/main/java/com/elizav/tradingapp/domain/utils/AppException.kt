package com.elizav.tradingapp.domain.utils

sealed class AppException(override val message: String) : Exception(message) {
    data class ApiException(override val message: String = API_EXCEPTION) : AppException(message)
    data class AuthException(override val message: String = AUTH_EXCEPTION) : AppException(message)
    data class ClientStateException(override val message: String = CLIENT_STATE_EXCEPTION) :
        AppException(message)

    data class DateParseException(override val message: String = DATE_PARSE_EXCEPTION) :
        AppException(message)

    companion object {
        const val API_EXCEPTION = "Api Error"
        const val AUTH_EXCEPTION = "Auth Error"
        const val CLIENT_STATE_EXCEPTION = "Illegal Client state"
        const val DATE_PARSE_EXCEPTION = "Date parse exception"
        const val SIGNALS_PARAMS_EXCEPTION="Please choose at least one pair. Dates must be correct"
    }
}
