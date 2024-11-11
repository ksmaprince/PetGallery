package com.khun.petgallery.presentation.ui.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.khun.petgallery.R
import com.khun.petgallery.utils.Constants

data class BottomNavigationItem(
    val title: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val screenRoute: String = ""
)

sealed class NavigationScreens(var screenRoute: String) {
    data object Home : NavigationScreens(Constants.HOME_ROUTES)
    data object MyFavourites : NavigationScreens(Constants.MY_FAVOURITES_ROUTES)
}

fun getBottomNavigationItems(context: Context): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = context.getString(R.string.nav_home),
            icon = Icons.Filled.Home,
            screenRoute = NavigationScreens.Home.screenRoute
        ),
        BottomNavigationItem(
            title = context.getString(R.string.nav_my_favourite),
            icon = Icons.Filled.Favorite,
            screenRoute = NavigationScreens.MyFavourites.screenRoute
        )
    )
}