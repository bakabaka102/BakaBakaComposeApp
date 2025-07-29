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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var remoteService: INewsService? = null

    // StateFlow theo chuẩn Kotlin, chỉ cần 1 biến để observe trạng thái kết nối
    private val _isServiceConnected = MutableStateFlow(false)
    val isServiceConnected: StateFlow<Boolean> = _isServiceConnected

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            remoteService = INewsService.Stub.asInterface(binder)
            Log.d("TAG", "Main_onServiceConnected")
            _isServiceConnected.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            remoteService = null
            Log.d("TAG", "Main_onServiceDisconnected")
            _isServiceConnected.value = false
        }
    }

    private fun bindRemoteService() {
        val intent = Intent(RemoteConstants.NEWS_APP_ACTION).apply {
            setPackage(RemoteConstants.PACKAGE_NEWS_APP_SERVER)
        }
        bindService(intent, serviceConnection, BIND_AUTO_CREATE).also {
            _isServiceConnected.value = it // Chỉ true nếu bind thành công, thực tế nên để state này do callback ServiceConnection cập nhật là chính.
        }
    }

    private fun unBindRemoteService() {
        if (_isServiceConnected.value) {
            unbindService(serviceConnection)
            remoteService = null
            _isServiceConnected.value = false
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
                val connected by isServiceConnected.collectAsState()

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

                if (connected) {
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
                            remoteService?.showLayout("Title")
                        }
                    }
                } else {
                    // Loading UI khi chưa kết nối Service
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                BackHandler(enabled = drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                }
            }
        }
    }
}
