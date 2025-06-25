package hn.news.app.ui.newsdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hn.news.app.ui.base.TopBar
import hn.news.app.ui.home.News


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    title: String,
    source: String,
    timeAgo: String,
    navController: NavController,
) {*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    navController: NavController,
) {
    val news = navController.previousBackStackEntry?.savedStateHandle?.get<News>("news")
    /* if (news != null) {
         NewsDetailScreen(news, navController)
     }
 */
    Scaffold(
        topBar = {
            TopBar(news?.title.toString()) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text(text = news?.title.toString(), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Nguồn: ${news?.source}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Thời gian: ${news?.timeAgo}", style = MaterialTheme.typography.bodySmall)
        }
    }
}