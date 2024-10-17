package com.baka.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baka.composeapp.ui.drawermenu.ContentOfScreen
import com.baka.composeapp.ui.drawermenu.DrawerContent
import com.baka.composeapp.ui.drawermenu.screen.Screens
import com.baka.composeapp.ui.drawermenu.screen.drawerItems
import com.baka.composeapp.ui.theme.BakaBakaComposeAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BakaBakaComposeAppTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val context = LocalContext.current
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val topBarTitle = when (currentRoute) {
                    Screens.Home.route -> Screens.Home.title
                    Screens.Profile.route -> Screens.Profile.title
                    Screens.Notification.route -> Screens.Notification.title
                    Screens.Setting.route -> Screens.Setting.title
                    Screens.ProductsScreen.route -> Screens.ProductsScreen.title
                    Screens.ProductDetailScreen.route -> Screens.ProductDetailScreen.title
                    else -> ""
                }
                ModalNavigationDrawer(
                    gesturesEnabled = drawerState.isOpen,
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(
                            drawerItems,
                            currentRoute,
                            context,
                            navController,
                            scope,
                            drawerState
                        )
                    },
                ) {
                    ContentOfScreen(topBarTitle, scope, drawerState, navController)
                }
            }
        }
    }
}
