package com.elizav.tradingapp.ui.navigation

import com.elizav.tradingapp.domain.model.Client

sealed class Route(val value: String) {
    sealed class RouteWithArgs(route: String, vararg params: String) : Route(route)

    object RootRoute : Route("root")
    object AuthRoute : Route("auth")
    object AuthGraphRoute : Route("auth_graph")

    object BottomGraphRoute : Route("bottom_graph") {
        const val CLIENT_KEY = "client"

        operator fun invoke(client: Client) {
            this.value.appendParams(
                CLIENT_KEY to client
            )
        }
    }

    object ProfileRoute : Route("profile")
    object SignalsListRoute : Route("signals_list")
    object PromoListRoute : Route("promo_list")
}