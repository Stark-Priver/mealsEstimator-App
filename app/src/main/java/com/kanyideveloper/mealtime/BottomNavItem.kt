
package com.kanyideveloper.mealtime

import com.kanyideveloper.mealtime.navigation.NavGraphs
import com.ramcosta.composedestinations.spec.NavGraphSpec

sealed class BottomNavItem(var title: String, var icon: Int, var screen: NavGraphSpec) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = com.joelkanyi.common.R.drawable.ic_home,
        screen = NavGraphs.home
    )

    data object KitchenTimer : BottomNavItem(
        title = "Timer",
        icon = com.joelkanyi.common.R.drawable.ic_timer,
        screen = NavGraphs.kitchenTimer
    )

    data object Favorites : BottomNavItem(
        title = "Favorites",
        icon = com.joelkanyi.common.R.drawable.ic_favorites,
        screen = NavGraphs.favorites
    )
    data object Settings : BottomNavItem(
        title = "Settings",
        icon = com.joelkanyi.common.R.drawable.ic_settings,
        screen = NavGraphs.settings
    )
}
