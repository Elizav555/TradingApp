package com.elizav.tradingapp.data.model.client

@kotlinx.serialization.Serializable
data class Client(
    val login: String,
    val partnerToken: String?,
    val peanutToken: String?
)
