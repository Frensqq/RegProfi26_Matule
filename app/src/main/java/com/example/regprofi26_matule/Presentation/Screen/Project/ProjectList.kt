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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.Screen.Component.MainTabBar
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

    LaunchedEffect(Unit) {
            viewModel.getProject()

    }

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
                        navController.navigate(NavigationRoutes.CREATE_PROJECT)
                    },
                tint = MatuleTheme.colors.inputIcon
            )

        }

        SpacerH(18)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            if (state.Projects != null) {
                items(state.Projects!!.items) {
                    PrimaryCard(it.title,
                        "",
                        it.typeProject,
                        true,
                        {viewModel.updateState(
                            state.copy(
                                currentProject = it
                            )
                        )
                            navController.navigate(NavigationRoutes.OPEN_PROJECT)
                        },
                        {
                    },
                        true
                    )
                }
            }

            item {
                SpacerH(80)
            }
        }

    }

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.background(Color.White)) {

            MainTabBar(
                navController,
                NavigationRoutes.PROJECT
            )
        }

    }


}