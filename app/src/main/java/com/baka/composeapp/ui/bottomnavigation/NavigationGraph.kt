package com.baka.composeapp.ui.bottomnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(navController, startDestination = BottomNavigationItems.Screen1.route) {
        composable(BottomNavigationItems.Screen1.route) {
            BottomNavigationItems.Screen1
            Screen1(innerPadding)
        }
        composable(BottomNavigationItems.Screen2.route) {
            BottomNavigationItems.Screen2
            Screen2(innerPadding)
        }
        composable(BottomNavigationItems.Screen3.route) {
            BottomNavigationItems.Screen3
            Screen3(innerPadding)
        }
    }
}