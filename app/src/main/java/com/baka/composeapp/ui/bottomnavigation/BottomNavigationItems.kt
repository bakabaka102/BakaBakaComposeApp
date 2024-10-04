package hn.single.ticktok.ui.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItems(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    data object Screen1 : BottomNavigationItems(
        route = "screen1",
        title = "Screen1",
        icon = Icons.Outlined.Home
    )

    data object Screen2 : BottomNavigationItems(
        route = "screen2",
        title = "Screen2",
        icon = Icons.Outlined.AddCircle
    )

    data object Screen3 : BottomNavigationItems(
        route = "screen3",
        title = "Screen3",
        icon = Icons.Outlined.AccountCircle
    )
}