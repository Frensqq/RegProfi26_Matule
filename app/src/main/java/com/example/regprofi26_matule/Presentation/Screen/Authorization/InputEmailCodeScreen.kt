package com.example.regprofi26_matule.Presentation.Screen.Authorization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.uikit.Bubbles.ButtonExit
import com.example.uikit.Inputs.InputInt
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography
import kotlinx.coroutines.delay

@Composable
fun InputEmailCodeScreen(viewModel: AuthViewModel, navController: NavHostController){

    val state = viewModel.state
    var timer by remember { mutableStateOf(60) }

    LaunchedEffect(timer) {
        if (timer!=0){
            delay(1000)
            timer--
        }
    }

    LaunchedEffect(state.pinCode) {
        if (state.pinCode.length == 6){
            viewModel.otpAuth(state.pinCode,navController)
        }
    }

    Column(Modifier.padding(horizontal = 20.dp, vertical = 68.dp).fillMaxWidth(), ) {
        ButtonExit {
            navController.navigate(NavigationRoutes.AUTH)
        }
        SpacerH(138)
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Введите код из Email",
                style = createMatuleTypography().title3Semibold,
                color = MatuleTheme.colors.black
            )
            SpacerH(20)
            val count = 6
            val values = remember { mutableStateListOf(*Array(count) { "" }) }
            val focuses = remember { List(count) { FocusRequester() } }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(count) { index ->
                    InputInt(
                        value = values[index],
                        onChangeValue = {
                            values[index] = it
                            viewModel.updateState(state.copy(pinCode = values.joinToString("")))
                            },
                        focusRequester = focuses[index],
                        index = index,
                        focuses = focuses
                    )
                }
            }

            SpacerH(20)

            Text(text =
                if (timer!=0)
                    "Отправить код повторно можно будет через $timer секунд"
            else "Отправить код", style = createMatuleTypography().textRegular,
                color = MatuleTheme.colors.placeholder,
                modifier = Modifier.fillMaxWidth(0.7f)
                    .clickable{
                        if (timer==0){
                            viewModel.otpRequest(navController)
                            timer=60
                        }
                    }, textAlign = TextAlign.Center
                )

        }
    }

}