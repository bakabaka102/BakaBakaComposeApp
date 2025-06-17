package hn.news.app.ui.home

//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

//NavigationBar is part of Material3, which is the recommended way to implement bottom navigation in Jetpack Compose.
@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("home", "explore", "profile")
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        "home" -> Icon(Icons.Default.Home, contentDescription = null)
                        "explore" -> Icon(Icons.Default.Search, contentDescription = null)
                        "profile" -> Icon(Icons.Default.Person, contentDescription = null)
                    }
                },
                label = {
                    Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                },
                selected = false,
                onClick = { navController.navigate(screen) }
            )
        }
    }
}

//Material lib BottomNavigation is deprecated, use NavigationBar instead
/*@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("home", "explore", "profile")
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        "home" -> Icon(Icons.Default.Home, contentDescription = null)
                        "explore" -> Icon(Icons.Default.Search, contentDescription = null)
                        "profile" -> Icon(Icons.Default.Person, contentDescription = null)
                    }
                },
                label = { Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }) },
                selected = false,
                onClick = { navController.navigate(screen) }
            )
        }
    }
}*/
