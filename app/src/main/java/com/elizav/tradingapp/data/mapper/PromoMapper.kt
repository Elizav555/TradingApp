package com.elizav.tradingapp.data.mapper

import com.elizav.tradingapp.domain.model.Promo
import com.elizav.tradingapp.data.model.promo.Promo as PromoData

object PromoMapper {
    fun PromoData.toDomain(name: String) = Promo(
        name = name,
        button_color = button_color,
        button_text = button_text,
        die_date = die_date,
        euro_available = euro_available,
        image = image,
        link = link,
        text = text
    )

    fun Promo.toData() = name to PromoData(
        button_color = button_color,
        button_text = button_text,
        die_date = die_date,
        euro_available = euro_available,
        image = image,
        link = link,
        text = text
    )
}