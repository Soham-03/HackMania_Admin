package com.example.hackmania_admin

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Listed : BottomBarScreen(
        route = "listed",
        title = "Listed",
        icon = R.drawable.list
    )

    object Analytics : BottomBarScreen(
        route = "analytics",
        title = "analytics",
        icon = R.drawable.anaylytics
    )

}
