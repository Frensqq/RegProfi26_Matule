package com.example.regprofi26_matule.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(): ViewModel() {

    fun launch(navHostController: NavHostController){
        viewModelScope.launch {
            delay(1500)

            if(UserRepository.Act){
                if (UserRepository.Pin == ""){
                    navHostController.navigate(NavigationRoutes.CREATE_PIN)
                }else{
                    navHostController.navigate(NavigationRoutes.INPUT_PIN)
                }
            }else{
                navHostController.navigate(NavigationRoutes.AUTH)
            }

        }
    }

}