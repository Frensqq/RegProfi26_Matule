package com.example.regprofi26_matule.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Domain.Repository.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun launch(navHostController: NavHostController) {

        viewModelScope.launch {

            delay(1500)

            if (userRepository.act) {

                if (userRepository.pin.isEmpty()) {
                    navHostController.navigate(
                        NavigationRoutes.CREATE_PIN
                    )
                } else {
                    navHostController.navigate(
                        NavigationRoutes.INPUT_PIN
                    )
                }

            } else {

                navHostController.navigate(
                    NavigationRoutes.AUTH
                )
            }
        }
    }
}