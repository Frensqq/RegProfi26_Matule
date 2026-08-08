package com.example.regprofi26_matule.Presentation.Screen.Authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.R
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Inputs.Inputs
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.SpacerW
import com.example.uikit.UI.createMatuleTypography

@Composable
fun AuthorizationScreen(viewModel: AuthViewModel, navigate: NavHostController){

    val state = viewModel.state
    var errorEmail by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 103.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {


        Row(modifier = Modifier.fillMaxWidth()) {
            Image(painter = painterResource(R.drawable.hello),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit)
            SpacerW(16)
            Text("Добро пожаловать!", style = createMatuleTypography().title1Heavy, color = MatuleTheme.colors.black)

        }

        SpacerH(25)

        Text("Войдите, чтобы пользоваться функциями приложения", style = createMatuleTypography().textRegular,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Left)

        SpacerH(63)

        Inputs(
            state.email,
            "example@mail.com",
            {
                viewModel.updateState(state.copy(email = it))
                errorEmail = ""
            },
            "Вход по E-mail",
            isError = errorEmail
        )
        SpacerH(13)
        Inputs(
            state.password,
            "Пароль",
            {
                viewModel.updateState(state.copy(password = it))
            },
            "Пароль",
            true
        )
        SpacerH(13)

        ButtonBig("Далее",
            {
                navigate.navigate(NavigationRoutes.CREATE_PIN)
            },state.email!= "" && state.password!=""
            )
        SpacerH(18)

        Text("Забыл пароль", style = createMatuleTypography().textRegular, textAlign =  TextAlign.Center, color = MatuleTheme.colors.accent,
            modifier = Modifier.clickable{
                if (state.email!=""){
                    navigate.navigate(NavigationRoutes.INPUT_CODE)
                }
                else{
                    errorEmail = "Введите email"
                }
            })

    }


}