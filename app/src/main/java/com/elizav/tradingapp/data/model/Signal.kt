package com.elizav.tradingapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Signal(
    @SerialName("ActualTime") val ActualTime: Long,
    @SerialName("Cmd") val Cmd: Int,
    @SerialName("Comment") val Comment: String,
    @SerialName("Id") val Id: Int,
    @SerialName("Pair") val Pair: String,
    @SerialName("Period") val Period: String,
    @SerialName("Price") val Price: Double,
    @SerialName("Sl") val Sl: Double,
    @SerialName("Tp") val Tp: Double,
    @SerialName("TradingSystem") val TradingSystem: Int
)