package com.baka.composeapp.ui.drawermenu

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerMenuItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val badgeCount: Int? = null,
)
