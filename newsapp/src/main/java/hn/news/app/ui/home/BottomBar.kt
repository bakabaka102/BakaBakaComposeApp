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
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hn.news.app.ui.base.Screens

//NavigationBar is part of Material3, which is the recommended way to implement bottom navigation in Jetpack Compose.
@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(Screens.HomeScreen.route, Screens.ExploreScreen.route, Screens.ProfileScreen().route)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    when (screen) {
                        Screens.HomeScreen.route -> Icon(Icons.Default.Home, contentDescription = null)
                        Screens.ExploreScreen.route -> Icon(Icons.Default.Search, contentDescription = null)
                        Screens.ProfileScreen().route -> Icon(Icons.Default.Person, contentDescription = null)
                    }
                },
                label = {
                    Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                },
                /*selected = selectedNavIndex == index, // <-- Chỉ tab được chọn mới nổi bật
                onClick = {
                    selectedNavIndex = index // <-- Đổi tab được chọn
                    navController.navigate(screen)
                }*/
                selected = currentRoute == screen, // Highlight theo route hiện tại
                onClick = {
                    if (currentRoute != screen) {
                        navController.navigate(screen) {
                            // Xóa các tab khác khỏi back stack, chỉ giữ lại màn hiện tại,
                            // xóa (pop) tất cả các màn hình trên back stack
                            // cho tới khi gặp màn hình có id là startDestination (ở đây thường là Home).
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true             // Giữ lại state của tab
                            }
                            launchSingleTop = true           // Không tạo nhiều instance cho 1 tab
                            restoreState = true              // Khôi phục lại state nếu có
                        }
                    }
                }
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

@Composable
@Preview
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}
