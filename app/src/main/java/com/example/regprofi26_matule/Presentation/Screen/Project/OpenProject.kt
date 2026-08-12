package com.example.regprofi26_matule.Presentation.Screen.Project

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Inputs.Inputs
import com.example.uikit.Inputs.InputsImage
import com.example.uikit.Selects.Select
import com.example.uikit.Selects.SelectDate
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography

@Composable
fun OpenProject(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state.currentProject

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {

        SpacerH(72)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                "Заказы",
                modifier = Modifier.padding(top = 2.dp),
                style = createMatuleTypography().title2Semibold,
                color = MatuleTheme.colors.black
            )
        }

        SpacerH(13)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Select(
                    state!!.typeProject,
                    "Выберите тип",
                    listOf(),
                    {

                    }, preview = "Тип одежды"
                )
            }
            item {
                Inputs(
                    state!!.title,
                    "Введите название",
                    {

                    },
                    title = "Название одежды",
                )
            }
            item {
                SelectDate(
                    state!!.dateStart,
                    "--.--.----",
                    {

                    }, preview = "Дата начала пошива"
                )
            }
            item {
                SelectDate(
                    state!!.dateEnd,
                    "--.--.----",
                    {

                    }, preview = "Дата Окончания пошива"
                )
            }
            item {
                Select(
                    state!!.category,
                    "Введите размер",
                    listOf(),
                    {

                    }, preview = "Размер"
                )
            }
            item {
                Inputs(
                    state!!.description_source,
                    "example.com",
                    {

                    },
                    title = "Источник описания",
                )
            }
            item {
                InputsImage(
                    onClick = {
                    },
                    painter = rememberAsyncImagePainter(viewModel.getImageUrl(state!!.collectionId,state!!.id, state!!.image)),
                    state =  true
                )
            }
            item {
                Column{
                    ButtonBig(
                        "Назад",
                        {
                            navController.navigate(NavigationRoutes.PROJECT)
                        }, true
                    )

                    SpacerH(90)
                }
            }

        }
    }

    val stateNav = viewModel.state

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.background(Color.White)) {
            TabBar({
                viewModel.updateState(stateNav.copy(tabBarState = "Главная"))
                navController.navigate(NavigationRoutes.MAIN)
            },{
                viewModel.updateState(stateNav.copy(tabBarState = "Каталог"))
                navController.navigate(NavigationRoutes.CATALOG)
            },{
                viewModel.updateState(stateNav.copy(tabBarState = "Заказы"))
            },{
                viewModel.updateState(stateNav.copy(tabBarState = "Профиль"))
                navController.navigate(NavigationRoutes.PROFILE)
            },stateNav.tabBarState)
        }

    }

}