package hn.news.app.ui.newsdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import hn.single.network.remote.model.Article

@Composable
fun MiniOverlayCard(
    article: Article,
    onFullScreen: () -> Unit,
    onClose: () -> Unit
) {
    var showActions by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 6.dp,
            shadowElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(72.dp)
                .clickable { showActions = !showActions },
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ảnh đại diện
                if (!article.urlToImage.isNullOrBlank()) {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(44.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        Modifier
                            .padding(start = 10.dp)
                            .size(44.dp)
                            .background(Color.Gray, CircleShape)
                    )
                }

                Spacer(Modifier.width(12.dp))

                // Tiêu đề
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // Khi showActions mới hiện các icon
                if (showActions) {
                    // Góc trái trên: Setting
                    Box(
                        Modifier
                            .size(32.dp)
                            .padding(4.dp)
                            .align(Alignment.Top)
                    ) {
                        IconButton(
                            onClick = {/* TODO: Xử lý setting */ },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = "Setting")
                        }
                    }
                    // Trung tâm: Fullscreen
                    IconButton(
                        onClick = onFullScreen,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Fullscreen")
                    }
                    // Góc phải trên: Close
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng")
                    }
                }
                Spacer(Modifier.width(6.dp))
            }
        }
    }
}