package com.elizav.tradingapp.ui.navigation

sealed class Route(val value: String) {
    sealed class RouteWithArgs(route: String, vararg params: String) : Route(route)

    object RootRoute : Route("root")
    object AuthRoute : Route("auth")
    object AuthGraphRoute : Route("auth_graph")

    object BottomGraphRoute : Route("bottom_graph") {
        const val TOKEN_KEY = "token"

        operator fun invoke(token: String) {
            this.value.appendParams(
                TOKEN_KEY to token
            )
        }
    }

    object ProfileRoute : Route("profile")
    object SignalsListRoute : Route("signals_list")
    object PromoListRoute : Route("promo_list")
}