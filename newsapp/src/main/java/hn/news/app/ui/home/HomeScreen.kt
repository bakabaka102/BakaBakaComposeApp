package hn.news.app.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hn.news.app.data.model.Article

@Composable
fun HomeScreen(viewModel: NewsViewModel = hiltViewModel(), onItemClicked: (Article) -> Unit = {}) {
    val tabs = listOf("Dành cho bạn", "Tin nổi bật", "Thế giới")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val newsList = viewModel.newsList
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

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
                    modifier = Modifier.background(color = backgroundColor, shape = RoundedCornerShape(10.dp)).height(40.dp),
                    text = {
                        Text(text = title, color = textColor)
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> {
                if (isLoading) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(Modifier.padding(16.dp))
                    }
                } else if (errorMessage != null) {
                    Text(
                        errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                } else {
                    NewsList(newsList, onItemClicked)
                }
            }

            else -> {
                if (isLoading) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(Modifier.padding(16.dp))
                    }
                } else if (errorMessage != null) {
                    Text(
                        errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                } else {
                    NewsList(newsList, onItemClicked)
                }
            }
            /*1 -> NewsList(newsListTrending, onItemClicked)
            2 -> NewsList(newsListWorld, onItemClicked)*/
        }
    }
}

@Composable
fun NewsList(newsList: List<Article>, onItemClicked: (Article) -> Unit = {}) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(newsList.size) { index ->
            NewsItem(newsList[index], onItemClicked)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun NewsItem(
    news: Article,
    onItemClicked: (Article) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onItemClicked(news) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F4FB)) // màu nền nhạt như demo
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nội dung bên trái (text)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = news.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = /*formatNewsDate*/(news.publishedAt),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }

            // Ảnh bên phải
            if (!news.urlToImage.isNullOrBlank()) {
                AsyncImage(
                    model = news.urlToImage,
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

/** Hàm định dạng ngày giờ theo mẫu (tuỳ chỉnh theo API) */
/*fun formatNewsDate(publishedAt: String): String {
    // Giả sử publishedAt là "2025-06-25T01:29:00Z"
    return try {
        val instant = java.time.Instant.parse(publishedAt)
        val date = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
        val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")
        formatter.format(date)
    } catch (e: Exception) {
        publishedAt
    }
}*/