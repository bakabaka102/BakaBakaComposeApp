package com.baka.composeapp.ui.drawermenu.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baka.composeapp.ui.bottomnavigation.BottomNavigationBar
import com.baka.composeapp.ui.bottomnavigation.BottomNavigationItems
import com.baka.composeapp.ui.bottomnavigation.Screen1
import com.baka.composeapp.ui.bottomnavigation.Screen2
import com.baka.composeapp.ui.bottomnavigation.Screen3

@Composable
fun SettingScreen(innerPadding: PaddingValues) {
    val navController: NavHostController = rememberNavController()
    Scaffold(modifier = Modifier
        .padding(top = innerPadding.calculateTopPadding())
        .fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItems.Screen1.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(BottomNavigationItems.Screen1.route) {
                //BottomNavigationItems.Screen1
                Screen1()
            }
            composable(BottomNavigationItems.Screen2.route) {
                //BottomNavigationItems.Screen2
                Screen2()
            }
            composable(BottomNavigationItems.Screen3.route) {
                //BottomNavigationItems.Screen3
                Screen3()
            }
        }
    }
}