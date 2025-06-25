package hn.news.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hn.news.app.ui.base.Screens
import hn.news.app.ui.explore.ExploreScreen
import hn.news.app.ui.home.BottomBar
import hn.news.app.ui.home.HomeScreen
import hn.news.app.ui.home.TopBarHome
import hn.news.app.ui.newsdetail.NewsDetailScreen
import hn.news.app.ui.profile.ProfileScreen
import hn.news.app.ui.theme.BakaBakaComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BakaBakaComposeAppTheme {
                NewsApp()
            }
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

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
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.HomeScreen.route) {
                HomeScreen(
                    onItemClicked = { news ->
                        //navController.navigate("detail/${news.title}/${news.source}/${news.timeAgo}")
                        navController.currentBackStackEntry?.savedStateHandle?.set("news", news)
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
                NewsDetailScreen(navController)
            }

            composable(Screens.ExploreScreen.route) {
                ExploreScreen(onBackClicked = { navController.popBackStack() })
            }

            composable(Screens.ProfileScreen().route) {
                ProfileScreen(onBackClicked = { navController.popBackStack() })
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 400)
@Composable
fun NewsAppPreview() {
    BakaBakaComposeAppTheme {
        NewsApp()
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    BakaBakaComposeAppTheme {
        TopBarHome()
    }
}