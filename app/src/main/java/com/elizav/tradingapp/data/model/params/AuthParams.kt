package com.elizav.tradingapp.data.model.params

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthParams(
    @SerialName("login") val login: String,
    @SerialName("password") val password: String
)
