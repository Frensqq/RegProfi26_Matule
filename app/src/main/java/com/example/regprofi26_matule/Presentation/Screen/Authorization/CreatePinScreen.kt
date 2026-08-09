package com.example.regprofi26_matule.Presentation.Screen.Authorization

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.Screen.Component.PinKeyboard
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography

@Composable
fun CreatePinScreen(naController: NavHostController, viewModel: AuthViewModel, stateScreen: Boolean){

    var pinCode by remember { mutableStateOf("") }

    LaunchedEffect(pinCode) {
        if (pinCode.length == 4){
            if (stateScreen){
                UserRepository.Pin = pinCode
                naController.navigate(NavigationRoutes.MAIN)
            }
            else{
                if (UserRepository.Pin == pinCode){
                    naController.navigate(NavigationRoutes.MAIN )
                }
            }
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        SpacerH(84)

        if (stateScreen) {
            Text(
                "Пропустить",
                style = createMatuleTypography().textRegular,
                color = MatuleTheme.colors.accent,
                modifier = Modifier.fillMaxWidth().clickable {

                },
                textAlign = TextAlign.Right
            )
        }
        SpacerH(40)

        Text(
            text = if(stateScreen) "Создайте пароль" else "Введите пароль",
            style = createMatuleTypography().title1Heavy,
            color = MatuleTheme.colors.black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        SpacerH(16)

        Text(
            text = "Для защиты ваших персональных данных",
            style = createMatuleTypography().textRegular,
            color = MatuleTheme.colors.placeholder,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        SpacerH(56)

        PinKeyboard(
            {
                pinCode = it
            }
        )

    }
    
}