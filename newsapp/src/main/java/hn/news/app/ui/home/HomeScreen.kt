package hn.news.app.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hn.news.app.NewsList

@Composable
fun HomeScreen() {
    val tabs = listOf("Dành cho bạn", "Tin nổi bật", "Thế giới")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> NewsList(newsListForYou)
            1 -> NewsList(newsListTrending)
            2 -> NewsList(newsListWorld)
        }
    }
}

data class News(val title: String, val source: String, val timeAgo: String)

val newsListForYou = listOf(
    News("Sự cố ChatGPT phơi bày sự phụ thuộc vào AI", "VnExpress", "5 giờ trước"),
    News("Nhiều người nhập viện vì hái 'lộc trời'", "Dân trí", "23 giờ trước"),
    News("Cẩn trọng với trào lưu hái nấm ở Đà Lạt", "Gia Lai", "Hôm qua"),
    News("Iran bị tấn công bởi máy bay không người lái", "Tiền Phong", "Hôm nay")
)

val newsListTrending = listOf(
    News("Tin nổi bật 1", "Nguồn 1", "1 giờ trước"),
    News("Tin nổi bật 2", "Nguồn 2", "2 giờ trước"),
    News("Tin nổi bật 3", "Nguồn 3", "3 giờ trước")
)

val newsListWorld = listOf(
    News("Thế giới 1", "Nguồn A", "1 giờ trước"),
    News("Thế giới 2", "Nguồn B", "2 giờ trước"),
    News("Thế giới 3", "Nguồn C", "3 giờ trước")
)