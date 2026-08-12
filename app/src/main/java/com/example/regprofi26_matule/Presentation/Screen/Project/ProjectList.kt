package com.example.regprofi26_matule.Presentation.Screen.Project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Card.PrimaryCard
import com.example.uikit.R
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.SpacerW
import com.example.uikit.UI.createMatuleTypography

@Composable
fun ProjectList(
    navController: NavHostController,
    viewModel: MainViewModel){

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {

        SpacerH(72)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            SpacerW(20)

            Text("Заказы",
                modifier = Modifier.padding(top = 2.dp),
                style = createMatuleTypography().title2Semibold,
                color = MatuleTheme.colors.black
            )

            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = null,
                modifier = Modifier.padding(6.dp)
                    .clickable{

                    },
                tint = MatuleTheme.colors.inputIcon
            )

        }

        SpacerH(18)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(1){
                PrimaryCard("test", "", "100", true, {}, {},true)
            }

            item {
                SpacerH(80)
            }
        }

    }

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
                navController.navigate(NavigationRoutes.PROFILE)
            },state.tabBarState)
        }

    }


}