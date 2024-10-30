package com.baka.composeapp.ui.drawermenu.screen.notification

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Animation
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screens(
    val title: String = "Animation",
    val unSelectedItem: ImageVector = Icons.Outlined.Animation,
    val selectedIcon: ImageVector = Icons.Filled.Animation,
) {

    data object AnimationScreen : Screens(
        title = "Animation",
        unSelectedItem = Icons.Outlined.Animation,
        selectedIcon = Icons.Filled.Animation,
    )

    data object ChartsScreen : Screens(
        title = "Chart",
        unSelectedItem = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart,
    )

    data object AccountScreen : Screens(
        title = "Account",
        unSelectedItem = Icons.Outlined.AccountBox,
        selectedIcon = Icons.Filled.AccountBox,
    )
}