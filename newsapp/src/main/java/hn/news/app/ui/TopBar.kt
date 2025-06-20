package hn.news.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hn.news.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = stringResource(R.string.news), onBackClicked: () -> Unit) {

    /*TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClicked }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    title,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )*/
    TopAppBar(
        title = { Text(
            text = title,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        ) },
        navigationIcon = {
            IconButton(onClick = { onBackClicked.invoke() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
            }
        }
    )
}