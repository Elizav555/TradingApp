package com.elizav.tradingapp.domain.model

import androidx.annotation.ColorInt

data class Promo(
    val name: String,
    @ColorInt val button_color: Int?,
    val button_text: String,
    val die_date: Int,
    val euro_available: Boolean,
    val image: String,
    val link: String,
    val text: String
)