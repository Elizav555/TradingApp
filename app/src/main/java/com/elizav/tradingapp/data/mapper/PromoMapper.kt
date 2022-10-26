package com.elizav.tradingapp.data.mapper

import androidx.core.graphics.toColorInt
import com.elizav.tradingapp.domain.model.Promo
import com.elizav.tradingapp.data.model.promo.Promo as PromoData

object PromoMapper {
    fun PromoData.toDomain(name: String) = Promo(
        name = name,
        button_color = try {
            button_color.toColorInt()
        } catch (ex: IllegalArgumentException) {
            null
        },
        button_text = button_text,
        die_date = die_date,
        euro_available = euro_available,
        image = image,
        link = link,
        text = text
    )
}