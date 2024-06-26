package com.ssafy.sungchef.features.screen.menu.navigation

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.ssafy.sungchef.features.screen.menu.MenuScreen

const val menuNavigationRoute = "menu_screen"

fun NavController.navigateMenu(
    navOptions: NavOptions? = null,
    menu: String
) {
    Log.d("여기임1", "MenuScreen: $menu")
    this.navigate(menuNavigationRoute.plus("/$menu"),navOptions)
}

fun NavGraphBuilder.menuScreen(
    navigateToMenu: (String) -> (Unit),
    navigateToMenuDetail: (Int) -> (Unit)
) {
    composable(menuNavigationRoute.plus("/{menu}")) {
        var menu = it.arguments?.getString("menu")
        if (menu == "-1") {
            menu = ""
        }
        Log.d("여기임2", "MenuScreen: $menu")
        MenuScreen(
            hiltViewModel(),
            menu = menu!!,
            onNavigateToMenu = navigateToMenu
        ) { recipeId ->
            navigateToMenuDetail(recipeId)
        }
    }
}