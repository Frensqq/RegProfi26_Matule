package com.example.regprofi26_matule.Presentation.Screen.Project

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.InputText
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Inputs.Inputs
import com.example.uikit.Inputs.InputsImage
import com.example.uikit.R
import com.example.uikit.Selects.Select
import com.example.uikit.Selects.SelectDate
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.SpacerW
import com.example.uikit.UI.createMatuleTypography

@Composable
fun CreateProjectScreen(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.selectImage(it, context)  // ← Сохраняем изображение в ViewModel
        }
    }

    // Получаем выбранное изображение из ViewModel
    val selectedImageUri = viewModel.selectedImageUri
    val painter = if (selectedImageUri != null) {
        rememberAsyncImagePainter(model = selectedImageUri)
    } else {
        null
    }

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
                    state.typeProject,
                    "Выберите тип",
                    state.typeListProject,
                    {
                        viewModel.updateState(state.copy(
                            typeProject = it
                        ))
                    }, preview = "Тип одежды"
                )
            }
            item {
                Inputs(
                    state.titleProject,
                    "Введите название",
                    {
                        viewModel.updateState(state.copy(
                            titleProject = it
                        ))
                    },
                    title = "Название одежды",
                )
            }
            item {
                SelectDate(
                    state.dateStart,
                    "--.--.----",
                    {
                        viewModel.updateState(
                            state.copy(dateStart = it)
                        )
                    }, preview = "Дата начала пошива"
                )
            }
            item {
                SelectDate(
                    state.dateEnd,
                    "--.--.----",
                    {
                        viewModel.updateState(
                            state.copy(dateEnd = it)
                        )
                    }, preview = "Дата Окончания пошива"
                )
            }
            item {
                Select(
                    state.categoryProject,
                    "Введите размер",
                    state.categoryListProject,
                    {
                        viewModel.updateState(state.copy(
                            categoryProject = it
                        ))
                    }, preview = "Размер"
                )
            }
            item {
                Inputs(
                    state.description_source,
                    "example.com",
                    {
                        viewModel.updateState(state.copy(
                            description_source = it
                        ))
                    },
                    title = "Источник описания",
                )
            }
            item {
                InputsImage(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    painter = painter,
                    state = selectedImageUri != null
                )
            }
            item {
                Column{
                    ButtonBig(
                        "Подтвердить",
                        {
                            viewModel.postProject(navController)
                        }, true
                    )

                    SpacerH(90)
                }
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