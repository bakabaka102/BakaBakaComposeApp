package com.baka.composeapp.ui.drawermenu

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerMenuItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int? = null,
)

sealed class Screens(var route: String) {

    data object Home : Screens("home")
    data object Profile : Screens("profile")
    data object Notification : Screens("notification")
    data object Setting : Screens("setting")
    data object ProductsScreen : Screens("products")
}
