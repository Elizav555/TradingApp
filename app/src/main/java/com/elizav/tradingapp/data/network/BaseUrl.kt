package com.elizav.tradingapp.data.network

sealed class BaseUrl(val value: String) {
    class PeanutBaseUrl(value: String = PEANUT_BASE_URL) : BaseUrl(value)
    class PartnerBaseUrl(value: String = PARTNER_BASE_URL) : BaseUrl(value)
    class PromoBaseUrl(value: String = PROMO_BASE_URL) : BaseUrl(value)
    companion object {
        private const val PEANUT_BASE_URL = "https://peanut.ifxdb.com/"
        private const val PARTNER_BASE_URL = "https://client-api.contentdatapro.com/"
        private const val PROMO_BASE_URL = "https://api-forexcopy.contentdatapro.com/"
    }
}