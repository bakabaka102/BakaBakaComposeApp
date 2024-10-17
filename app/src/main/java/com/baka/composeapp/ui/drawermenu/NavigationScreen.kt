package com.baka.composeapp.ui.drawermenu

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.baka.composeapp.helper.Logger
import com.baka.composeapp.ui.drawermenu.screen.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    items: List<Screens>,
    currentRoute: String?,
    context: Context,
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    ModalDrawerSheet {
        NavBarHeader()
        Spacer(modifier = Modifier.height(8.dp))
        NavBarBody(
            items = items,
            currentRoute = currentRoute,
        ) { currentNavigationItem ->
            if (currentNavigationItem.route == "share") {
                Toast.makeText(context, "Share", Toast.LENGTH_LONG).show()
            } else {
                navController.navigate(currentNavigationItem.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                        // Pop up to the start destination, clearing the back stack
                        popUpTo(startDestinationRoute) {
                            // Save the state of popped destinations
                            saveState = true
                        }
                    }

                    // Configure navigation to avoid multiple instances of the same destination
                    launchSingleTop = true

                    // Restore state when re-selecting a previously selected item
                    restoreState = true
                }
            }

            scope.launch {
                drawerState.close()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ContentOfScreen(
    topBarTitle: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = topBarTitle)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu"
                        )
                    }
                })
        }
    ) { innerPadding ->
        Logger.d("PaddingValue ===== $innerPadding")
        SetUpNavGraph(navController = navController, innerPadding = innerPadding)
    }
}