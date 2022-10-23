package com.elizav.tradingapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoParams(
    @SerialName("login") val login: String,
    @SerialName("token") val token: String
)