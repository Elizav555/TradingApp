package com.elizav.tradingapp.domain.model.client

data class Client(
    val login: String,
    val partnerToken: String?,
    val peanutToken: String?
)
