package com.elizav.tradingapp.data.model.promo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Promo(
    @SerialName("button_color") val button_color: String,
    @SerialName("button_text") val button_text: String,
    @SerialName("die_date") val die_date: Int,
    @SerialName("euro_available") val euro_available: Boolean,
    @SerialName("image") val image: String,
    @SerialName("link") val link: String,
    @SerialName("text") val text: String
)