package hn.news.app.ui.newsdetail

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hn.news.app.data.model.Article
import hn.news.app.ui.base.NewsDetailTopBar
import hn.news.app.ui.base.Screens

@Composable
fun NewsDetailScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onDropDownClick: (Article) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    val news =
        navController.previousBackStackEntry?.savedStateHandle?.get<Article>(Screens.NewsDetailScreen.title)

    Scaffold(
        topBar = {
            NewsDetailTopBar(
                news?.title.toString(),
                onBackClick = onBackClick,
                onDropDownClick = { news?.let { onDropDownClick(it) } },
                onFilterClick = onFilterClick,
                onMenuClick = onMenuClick,
            )
        }
    ) { innerPadding ->
        news?.let {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Ảnh minh họa
                if (!news.urlToImage.isNullOrBlank()) {
                    AsyncImage(
                        model = news.urlToImage,
                        contentDescription = "Ảnh minh họa",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Tiêu đề
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nguồn và thời gian
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Nguồn: ${news.source.name}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF757575)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Thời gian: ${news.publishedAt}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF757575)
                    )
                }

                // Tác giả
                if (!news.author.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tác giả: ${news.author}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                if (!news.description.isNullOrBlank()) {
                    Text(
                        text = news.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Nội dung chính
                if (news.content.isNullOrBlank().not()) {
                    Text(
                        text = news.content,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Link gốc
                Spacer(modifier = Modifier.height(16.dp))
                val context = LocalContext.current
                Text(
                    text = "Xem bài gốc",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, news.url.toUri())
                            context.startActivity(intent, null)
                        }
                        .padding(4.dp)
                )
            }
        } ?: run {
            // Hiển thị khi chưa có dữ liệu
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Không có dữ liệu bài viết.", color = Color.Gray)
            }
        }
    }
}