package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    var route: String,
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unSelectedIcon: ImageVector? = null,
    val badgeCount: Int? = null,
) {

    data object Home :
        Screens(
            route = "home",
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
        )

    data object Profile : Screens(
        route = "profile",
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
    )

    data object Notification : Screens(
        route = "notification",
        title = "Notification",
        selectedIcon = Icons.Filled.Notifications,
        unSelectedIcon = Icons.Outlined.Notifications,
        badgeCount = 6,
    )

    data object Setting : Screens(
        route = "setting",
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
    )

    data object ProductsScreen : Screens(
        route = "products",
        title = "Products",
        selectedIcon = Icons.Filled.Payments,
        unSelectedIcon = Icons.Outlined.Payment,
    )

    data object Share : Screens(
        title = "Share",
        route = "share",
        selectedIcon = Icons.Filled.Share,
        unSelectedIcon = Icons.Outlined.Share,
    )

    data object ProductDetailScreen :
        Screens(route = "product_detail_screen", title = "Product detail")
}