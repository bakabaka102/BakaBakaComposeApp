package hn.news.app.ui

sealed class Screens(
    open var route: String,
    open val title: String,
) {

    data object HomeScreen : Screens("HomeScreen", "HomeScreen")
    data object ExploreScreen : Screens("ExploreScreen", "ExploreScreen")
    data class ProfileScreen(
        override var route: String = "ProfileScreen",
        override var title: String = "ProfileScreen"
    ) : Screens(route, title)

}