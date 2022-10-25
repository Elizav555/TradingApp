package com.elizav.tradingapp.ui.navigation

import com.elizav.tradingapp.domain.model.client.Client

sealed class Route(open val value: String) {
    object RootRoute : Route("root")
    object AuthRoute : Route("auth")
    object AuthGraphRoute : Route("auth_graph")

    data class BottomGraphRoute(override var value: String = "bottom_graph") : Route(value) {
        operator fun invoke(client: Client): BottomGraphRoute {
            value = value.appendParams(
                CLIENT_LOGIN to client.login,
                CLIENT_PEANUT_TOKEN to client.peanutToken,
                CLIENT_PARTNER_TOKEN to client.partnerToken
            )
            return this
        }

        companion object {
            const val CLIENT_LOGIN = "client login"
            const val CLIENT_PEANUT_TOKEN = "client peanut token"
            const val CLIENT_PARTNER_TOKEN = "client partner token"
        }
    }

    data class WebViewRoute(override var value: String = "web_view") : Route(value) {
        operator fun invoke(url: String): WebViewRoute {
            value = value.appendParams(
                URL_KEY to url
            )
            return this
        }

        companion object {
            const val URL_KEY = "url key"
        }
    }

    object ProfileRoute : Route("profile")
    object SignalsListRoute : Route("signals_list")
    object PromoListRoute : Route("promo_list")
}