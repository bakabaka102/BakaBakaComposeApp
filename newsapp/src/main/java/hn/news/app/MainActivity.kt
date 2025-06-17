package hn.news.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hn.news.app.ui.Screens
import hn.news.app.ui.explore.ExploreScreen
import hn.news.app.ui.home.BottomBar
import hn.news.app.ui.home.HomeScreen
import hn.news.app.ui.home.TopBar
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.HomeScreen.route) { HomeScreen() }
            composable(Screens.ExploreScreen.route) {  ExploreScreen()  }
            composable(Screens.ProfileScreen().route) { ProfileScreen() }
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
        TopBar()
    }
}