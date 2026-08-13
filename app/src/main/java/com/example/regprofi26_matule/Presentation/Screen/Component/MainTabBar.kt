package com.example.regprofi26_matule.Presentation.Screen.Component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.uikit.Tabbar.TabBar

@Composable
fun MainTabBar(
    navController: NavHostController,
    currentRoute: String
) {

    fun navigate(route: String) {
        if (currentRoute == route) return

        navController.navigate(route) {
            popUpTo(NavigationRoutes.MAIN) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

    TabBar(
        {
            navigate(NavigationRoutes.MAIN)
        },
        {
            navigate(NavigationRoutes.CATALOG)
        },
        {
            navigate(NavigationRoutes.PROJECT)
        },
        {
            navigate(NavigationRoutes.PROFILE)
        },
        when (currentRoute) {
            NavigationRoutes.MAIN -> "Главная"
            NavigationRoutes.CATALOG -> "Каталог"
            NavigationRoutes.PROJECT,
            NavigationRoutes.CREATE_PROJECT,
            NavigationRoutes.OPEN_PROJECT -> "Заказы"
            NavigationRoutes.PROFILE -> "Профиль"
            else -> "Главная"
        }
    )
}