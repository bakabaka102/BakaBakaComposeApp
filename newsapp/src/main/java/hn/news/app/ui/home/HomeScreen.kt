package hn.news.app.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onItemClicked: (News) -> Unit = {}) {
    val tabs = listOf("Dành cho bạn", "Tin nổi bật", "Thế giới")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column {
        TopBarHome()
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {}, // Ẩn gạch chân
            divider = {} // Ẩn đường kẻ dưới TabRow nếu cần
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent,
                    animationSpec = tween(durationMillis = 300)
                )

                val textColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                    animationSpec = tween(durationMillis = 300)
                )
                Tab(
                    selected = isSelected,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.background(color = backgroundColor, shape = RoundedCornerShape(6.dp)),
                    text = {
                        Text(text = title, color = textColor)
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> NewsList(newsListForYou, onItemClicked)
            1 -> NewsList(newsListTrending, onItemClicked)
            2 -> NewsList(newsListWorld, onItemClicked)
        }
    }
}

@Composable
fun NewsList(newsList: List<News>, onItemClicked: (News) -> Unit = {}) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(newsList.size) { index ->
            NewsItem(newsList[index], onItemClicked)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun NewsItem(news: News, onItemClicked: (News) -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            onItemClicked.invoke(news)
        },
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