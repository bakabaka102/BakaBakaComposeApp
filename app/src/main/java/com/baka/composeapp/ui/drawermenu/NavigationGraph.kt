package com.baka.composeapp.ui.drawermenu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.baka.composeapp.ui.drawermenu.screen.NotificationScreen
import com.baka.composeapp.ui.drawermenu.screen.ProductListPage
import com.baka.composeapp.ui.drawermenu.screen.HomeScreen
import com.baka.composeapp.ui.drawermenu.screen.ProfileScreen
import com.baka.composeapp.ui.drawermenu.screen.SettingScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(innerPadding = innerPadding)
        }
        composable(Screens.Notification.route) {
            NotificationScreen(innerPadding = innerPadding)
        }
        composable(Screens.Profile.route) {
            ProfileScreen(innerPadding = innerPadding)
        }

        composable(Screens.Setting.route) {
            SettingScreen(innerPadding = innerPadding)
        }

        composable(route = Screens.ProductsScreen.route) {
            ProductListPage(innerPadding = innerPadding)
        }
    }
}


