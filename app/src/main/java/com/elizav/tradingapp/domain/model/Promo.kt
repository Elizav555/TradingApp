package com.elizav.tradingapp.domain.model

data class Promo(
    val name: String,
    val button_color: String,
    val button_text: String,
    val die_date: Int,
    val euro_available: Boolean,
    val image: String,
    val link: String,
    val text: String
)