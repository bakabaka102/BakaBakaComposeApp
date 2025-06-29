package hn.news.app.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun NewsDetailTopBar(
    title: String = stringResource(R.string.app_name),
    source: String = stringResource(R.string.app_name),
    onBackClick: () -> Unit,
    onDropDownClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = source,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                IconButton(onClick = {
                    onDropDownClick.invoke()
                }
                ) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown")
                }
                IconButton(onClick = onFilterClick) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Filter")
                }
            }
        },
        actions = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun NewsDetailTopBarPreview() {
    NewsDetailTopBar(onBackClick = {})
}
