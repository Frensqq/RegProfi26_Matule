package com.example.regprofi26_matule.Presentation.Screen.Authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Screen.Component.validAge
import com.example.regprofi26_matule.Presentation.Screen.Component.validPhone
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Inputs.Inputs
import com.example.uikit.Selects.Select
import com.example.uikit.Selects.SelectDate
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography

@Composable
fun CreateProfileScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {

    val state = viewModel.state

    var errorDate by remember { mutableStateOf<String?>(null) }
    var errorPhone by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        SpacerH(76)

        Text(
            "Создание профиля",
            style = createMatuleTypography().title1Heavy
        )

        SpacerH(44)

        Text(
            "Без профиля вы не сможете создавать проекты.",
            style = createMatuleTypography().captionRegular,
            color = MatuleTheme.colors.description
        )

        SpacerH(8)

        Text(
            "В профиле будут храниться результаты проектов и ваши описания.",
            style = createMatuleTypography().captionRegular,
            color = MatuleTheme.colors.description
        )

        SpacerH(32)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(23.dp)
        ) {

            item {
                Inputs(
                    state.name,
                    "Имя",
                    {
                        viewModel.updateState(
                            state.copy(name = it)
                        )
                    }
                )
            }

            item {
                Inputs(
                    state.patronymic,
                    "Отчество",
                    {
                        viewModel.updateState(
                            state.copy(patronymic = it)
                        )
                    }
                )
            }

            item {
                Inputs(
                    state.surname,
                    "Фамилия",
                    {
                        viewModel.updateState(
                            state.copy(surname = it)
                        )
                    }
                )
            }

            item {
                SelectDate(
                    state.dateUser,
                    "Дата рождения",
                    {
                        viewModel.updateState(
                            state.copy(dateUser = it)
                        )
                        errorDate = null
                    },
                )
            }

            item {
                Select(
                    state.gender,
                    "Пол",
                    listOf("Мужской", "Женский"),
                    {
                        viewModel.updateState(
                            state.copy(gender = it)
                        )
                    }
                )
            }

            item {
                Inputs(
                    state.phone,
                    "+79991234567",
                    {
                        viewModel.updateState(
                            state.copy(phone = it)
                        )
                        errorPhone = null
                    },
                    "Телефон",
                    isError = errorPhone
                )
            }
        }
    }

    val stateButton =
        state.name.isNotEmpty() &&
                state.surname.isNotEmpty() &&
                state.patronymic.isNotEmpty() &&
                state.gender.isNotEmpty() &&
                state.dateUser.isNotEmpty() &&
                state.phone.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 33.dp
            ),
        contentAlignment = Alignment.BottomCenter
    ) {

        ButtonBig(
            "Создать + $errorDate",
            {
                val ageValid = validAge(state.dateUser)
                val phoneValid = validPhone(state.phone)

                errorDate =
                    if (ageValid)
                        null
                    else
                        "Возраст должен быть не менее 18 лет"

                errorPhone =
                    if (phoneValid)
                        null
                    else
                        "Введите +7XXXXXXXXXX или 8XXXXXXXXXX"

                if (ageValid && phoneValid) {
                    viewModel.patchUser(navController)
                }
            },
            stateButton
        )
    }
}