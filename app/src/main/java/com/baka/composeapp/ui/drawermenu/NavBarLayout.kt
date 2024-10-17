package com.baka.composeapp.ui.drawermenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baka.composeapp.R
import com.baka.composeapp.ui.drawermenu.screen.Screens

@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 10.dp)
        )
        Text(
            text = "Baka baka",
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun NavBarBody(
    items: List<Screens>,
    currentRoute: String?,
    onClick: (Screens) -> Unit,
) {
    items.forEach { navigationItem ->
        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(),
            label = {
                Text(text = navigationItem.title)
            },
            selected = currentRoute == navigationItem.route,
            onClick = {
                onClick(navigationItem)
            },
            icon = {
                (if (currentRoute == navigationItem.route) {
                    navigationItem.selectedIcon
                } else {
                    navigationItem.unSelectedIcon
                })?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = navigationItem.title
                    )
                }
            },
            badge = {
                navigationItem.badgeCount?.let {
                    Text(text = it.toString())
                }
            },
            modifier = Modifier.padding(
                PaddingValues(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
            )
        )
    }
}