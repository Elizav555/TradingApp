package com.elizav.tradingapp.data.model.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("extensionData") val extensionData: ExtensionData,
    @SerialName("result") val result: Boolean,
    @SerialName("token") val token: String
)