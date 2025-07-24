package com.baka.composeapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import com.hn.libs.INewsService
import com.hn.libs.RemoteConstants
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var remoteService: INewsService? = null
    private var isBindService: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            remoteService = INewsService.Stub.asInterface(binder)
            isBindService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            remoteService = null
            isBindService = false
        }
    }

    private fun bindRemoteService() {
        /*val intent = Intent().apply {
            component = ComponentName(
                "hn.news.app",                      // package name của newsapp
                "hn.news.app.service.NewsService"  // full class name của Service
            )
        }*/

        val intent = Intent(RemoteConstants.NEWS_APP_ACTION).apply {
            setPackage(RemoteConstants.PACKAGE_NEWS_APP_SERVER)
        }
        isBindService = this.bindService(intent, serviceConnection, BIND_AUTO_CREATE).also {
            Log.d("TAG", "bindRemoteService: $it")
        }
    }

    private fun unBindRemoteService() {
        if (isBindService) {
            isBindService = false
            remoteService = null
            this.unbindService(serviceConnection)
        }
    }

    override fun onStart() {
        super.onStart()
        bindRemoteService()
    }

    override fun onDestroy() {
        unBindRemoteService()
        super.onDestroy()
    }

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
                    ContentOfScreen(topBarTitle, scope, drawerState, navController) {
                        remoteService?.showLayout("Name")?.let {
                            bindRemoteService()
                        }
                    }
                }
                BackHandler(enabled = drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                }
            }
        }
    }
}
