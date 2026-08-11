package com.example.regprofi26_matule.Presentation.Screen.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Tabbar.TabBar

@Composable
fun ProfileScreen(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.background(Color.White)) {
            TabBar({
                viewModel.updateState(state.copy(tabBarState = "Главная"))
                navController.navigate(NavigationRoutes.MAIN)
            },{
                viewModel.updateState(state.copy(tabBarState = "Каталог"))
                navController.navigate(NavigationRoutes.CATALOG)
            },{
                viewModel.updateState(state.copy(tabBarState = "Заказы"))
            },{
                viewModel.updateState(state.copy(tabBarState = "Профиль"))
            },state.tabBarState)
        }

    }
}