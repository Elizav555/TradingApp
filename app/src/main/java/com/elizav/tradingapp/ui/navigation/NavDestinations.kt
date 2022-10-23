package com.elizav.tradingapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.elizav.tradingapp.R
import com.elizav.tradingapp.ui.navigation.Route.BottomGraphRoute.Companion.CLIENT_LOGIN
import com.elizav.tradingapp.ui.navigation.Route.BottomGraphRoute.Companion.CLIENT_PARTNER_TOKEN
import com.elizav.tradingapp.ui.navigation.Route.BottomGraphRoute.Companion.CLIENT_PEANUT_TOKEN

sealed class Destination(val route: Route, @StringRes val resourceId: Int, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route.value else {
        val builder = StringBuilder(route.value)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: Route, @StringRes resourceId: Int) :
        Destination(route, resourceId) {
        operator fun invoke(): Route = route
    }

    sealed class BottomNavDestination(
        route: Route,
        @StringRes resourceId: Int,
        val iconImageVector: ImageVector
    ) : Destination(route, resourceId)

    object AuthGraphDestination : NoArgumentsDestination(Route.AuthGraphRoute, R.string.auth)
    object AuthDestination : NoArgumentsDestination(Route.AuthRoute, R.string.login)
    object BottomGraphDestination : Destination(
        Route.BottomGraphRoute(),
        R.string.bottom_host,
        CLIENT_LOGIN,
        CLIENT_PEANUT_TOKEN,
        CLIENT_PARTNER_TOKEN,
    )

    object ProfileDestination :
        BottomNavDestination(
            Route.ProfileRoute,
            R.string.profile,
            Icons.Default.Person,
        )

    object SignalsListDestination :
        BottomNavDestination(Route.SignalsListRoute, R.string.signals_list, Icons.Default.Info)

    object PromoListDestination :
        BottomNavDestination(Route.PromoListRoute, R.string.promo_list, Icons.Default.Favorite)
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}