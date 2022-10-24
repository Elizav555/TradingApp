package com.elizav.tradingapp.data.model.promo

import javax.xml.bind.annotation.XmlElement

data class PromoResponse(
    @XmlElement(name = "GetCCPromoResponse", nillable = false, required = true)
    var GetCCPromoResult: String
)

