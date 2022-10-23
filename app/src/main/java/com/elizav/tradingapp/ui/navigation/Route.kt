package com.elizav.tradingapp.ui.navigation

import com.elizav.tradingapp.domain.model.Client

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

    object ProfileRoute : Route("profile")
    object SignalsListRoute : Route("signals_list")
    object PromoListRoute : Route("promo_list")
}