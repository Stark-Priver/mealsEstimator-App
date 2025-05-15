/*
 * Copyright 2022 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
