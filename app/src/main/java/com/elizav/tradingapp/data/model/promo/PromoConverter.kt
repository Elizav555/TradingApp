package com.elizav.tradingapp.data.model.promo

import javax.inject.Inject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PromoConverter @Inject constructor() {
    operator fun invoke(responseBody: String): Map<String, Promo> {
        val dataString = dataInResponseRegex.find(responseBody)?.groups?.get(1)?.value
        val formattedDataString = dataString?.replace(" ", "")?.removePrefix("{")
            ?.removeSuffix("}")
        return formattedDataString?.let { data ->
                val promoNames = promoNamesInDataRegex.findAll(data)
                    .mapNotNull { match -> match.groups[1]?.value }.toList()

                val promoMap = mutableMapOf<String, Promo>()

                promoNames.forEach { name ->
                    getPromoByNameInDataRegex(name)
                        .find(data)?.groups?.get(1)?.value?.let { promo ->
                            promoMap[name] = Json.decodeFromString<Promo>(promo)
                        }
                }
                return@let promoMap
            } ?: emptyMap()
    }

    companion object{
        val dataInResponseRegex = """<GetCCPromoResult>(.*)</GetCCPromoResult>""".toRegex()
        val promoNamesInDataRegex = """"([a-z,_]*)":\{""".toRegex()
        fun getPromoByNameInDataRegex(name:String) = """"$name":(\{[^{}]+\})""".toRegex()
    }
}