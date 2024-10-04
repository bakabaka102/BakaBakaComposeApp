package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeTab(val title: String)

data class TabItem(
    val title: String, val unSelectedItem: ImageVector, val selectedIcon: ImageVector
)

val tabItem = listOf(
    TabItem(
        title = "Home", unSelectedItem = Icons.Outlined.Home, selectedIcon = Icons.Filled.Home
    ), TabItem(
        title = "Browse",
        unSelectedItem = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart
    ), TabItem(
        title = "Settings",
        unSelectedItem = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )
)

enum class HomeTabs(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val text: String
) {
    Shop(
        unselectedIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart,
        text = "Shop"
    ),
    Favourite(
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        text = "Favourite"
    ),
    Profile(
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        text = "Profile"
    )
}