package hn.news.app

//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hn.news.app.ui.Screens
import hn.news.app.ui.home.BottomBar
import hn.news.app.ui.home.HomeScreen
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
            composable(Screens.ExploreScreen.route) { /* ExploreScreen() */ }
            composable(Screens.ProfileScreen().route) { /* ProfileScreen() */ }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Google News") },
    )
}

@Composable
fun NewsList(newsList: List<News>) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(newsList.size) { index ->
            NewsItem(newsList[index])
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun NewsItem(news: News) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = news.title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${news.source} • ${news.timeAgo}",
                style = MaterialTheme.typography.bodyMedium
            )
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