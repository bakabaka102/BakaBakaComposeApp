package hn.news.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hn.news.app.ui.base.Screens
import hn.news.app.ui.explore.ExploreScreen
import hn.news.app.ui.home.BottomBar
import hn.news.app.ui.home.HomeScreen
import hn.news.app.ui.home.TopBarHome
import hn.news.app.ui.newsdetail.MiniOverlayCard
import hn.news.app.ui.newsdetail.NewsDetailScreen
import hn.news.app.ui.profile.ProfileScreen
import hn.news.app.ui.theme.BakaBakaComposeAppTheme
import hn.single.network.remote.model.Article
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BakaBakaComposeAppTheme {
                MainScreen()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
        }
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${this.packageName}".toUri()
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)
        }
    }
}

@Composable
fun MainScreen() {

    var miniArticle by remember { mutableStateOf<Article?>(null) }
    var pendingMiniArticle by remember { mutableStateOf<Article?>(null) }

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    LaunchedEffect(currentDestination?.route) {
        if (currentDestination?.route == Screens.HomeScreen.route && pendingMiniArticle != null) {
            miniArticle = pendingMiniArticle
            pendingMiniArticle = null
        }
    }
    val bottomBarRoutes = listOf(
        Screens.HomeScreen.route,
        Screens.ExploreScreen.route,
        Screens.ProfileScreen().route
    )

    val shouldShowBottomBar = currentDestination?.hierarchy?.any { destination ->
        destination.route in bottomBarRoutes
    } == true
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Log.d("HomeScreen", "Current route --- ${currentDestination?.route}")
        Log.d("HomeScreen", "Current route: Article --- $miniArticle")
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.HomeScreen.route) {
                HomeScreen(
                    onItemClicked = { news ->
                        //navController.navigate("detail/${news.title}/${news.source}/${news.timeAgo}")
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Screens.NewsDetailScreen.title,
                            news
                        )
                        navController.navigate(Screens.NewsDetailScreen.route)

                    }
                )
            }

            /*composable("detail/{title}/{source}/{timeAgo}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val source = backStackEntry.arguments?.getString("source") ?: ""
                val timeAgo = backStackEntry.arguments?.getString("timeAgo") ?: ""
                NewsDetailScreen(title, source, timeAgo, navController)
            }*/

            composable(
                route = Screens.NewsDetailScreen.route,
                /* arguments = listOf(
                     navArgument("news") {
                         type = NavType.ParcelableType(News::class.java)
                     }
                 )*/
            ) { backStackEntry ->
                /*val news = backStackEntry.arguments?.getParcelable<News>("news")
                news?.let {
                }*/
                NewsDetailScreen(navController, onBackClick = {
                    navController.popBackStack()
                }, onDropDownClick = { article ->
                    //miniArticle = article
                    pendingMiniArticle = article
                    navController.popBackStack() // Về HomeScreen
                })
            }

            composable(Screens.ExploreScreen.route) {
                ExploreScreen(onBackClick = { navController.popBackStack() })
            }

            composable(Screens.ProfileScreen().route) {
                ProfileScreen(onBackClick = { navController.popBackStack() })
            }
        }
        // Mini overlay nổi lên trên Home
        // Chỉ hiện MiniOverlayCard khi đang ở HomeScreen
        //if (miniArticle != null && currentDestination?.route == Screens.HomeScreen.route) {
            /*AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {*/
                //if (currentDestination?.route == Screens.HomeScreen.route) {
                    miniArticle?.let { article ->
                        MiniOverlayCard(
                            article = article,
                            onFullScreen = {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set(Screens.NewsDetailScreen.title, article)
                                navController.navigate(Screens.NewsDetailScreen.route)
                            },
                            onClose = {
                                miniArticle = null
                            }
                        )
                    }
                //}
            //}
        //}
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun NewsAppPreview() {
    BakaBakaComposeAppTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    BakaBakaComposeAppTheme {
        TopBarHome()
    }
}