package hn.news.app.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import hn.news.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String = stringResource(R.string.app_name),
    onBackClick: () -> Unit,
    onCustomIconClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                IconButton(onClick = onCustomIconClick) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown, // Hoặc icon bạn muốn, ví dụ Icons.Default.Share
                        contentDescription = "Custom Icon"
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
    TopBar(onBackClick = {})
}
