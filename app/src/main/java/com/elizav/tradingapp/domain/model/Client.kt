package com.elizav.tradingapp.domain.model

data class Client(
    val login: String,
    val peanutToken: String?,
    val partnerToken: String?
)
