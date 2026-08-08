package com.example.regprofi26_matule.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.regprofi26_matule.Presentation.Screen.Authorization.AuthorizationScreen
import com.example.regprofi26_matule.Presentation.Screen.Splash.SplashScreen
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.SplashViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation(isOnline: Boolean){

    val navController = rememberNavController()
    val splashViewModel: SplashViewModel = koinViewModel()
    val authViewModel: AuthViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH){

        composable(NavigationRoutes.SPLASH){
            SplashScreen(navController,splashViewModel )
        }
        composable(NavigationRoutes.AUTH) {
            AuthorizationScreen(authViewModel, navController)
        }
        composable(NavigationRoutes.CREATE_PIN) {

        }
        composable(NavigationRoutes.INPUT_CODE){

        }
        composable(NavigationRoutes.CREATE_PASS) {

        }
        composable(NavigationRoutes.CREATE_USER) {

        }
    }
}