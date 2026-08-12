package com.example.regprofi26_matule.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.regprofi26_matule.Presentation.Screen.Authorization.AuthorizationScreen
import com.example.regprofi26_matule.Presentation.Screen.Authorization.CreatePasswordScreen
import com.example.regprofi26_matule.Presentation.Screen.Authorization.CreatePinScreen
import com.example.regprofi26_matule.Presentation.Screen.Authorization.CreateProfileScreen
import com.example.regprofi26_matule.Presentation.Screen.Authorization.InputEmailCodeScreen
import com.example.regprofi26_matule.Presentation.Screen.Market.CartScreen
import com.example.regprofi26_matule.Presentation.Screen.Market.CatalogScreen
import com.example.regprofi26_matule.Presentation.Screen.Market.MainScreen
import com.example.regprofi26_matule.Presentation.Screen.Profile.ListOrder
import com.example.regprofi26_matule.Presentation.Screen.Profile.ProfileScreen
import com.example.regprofi26_matule.Presentation.Screen.Splash.SplashScreen
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.SplashViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation(isOnline: Boolean){

    val navController = rememberNavController()
    val splashViewModel: SplashViewModel = koinViewModel()
    val authViewModel: AuthViewModel = koinViewModel()
    val mainViewModel: MainViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH){

        composable(NavigationRoutes.SPLASH){
            SplashScreen(navController,splashViewModel )
        }
        composable(NavigationRoutes.AUTH) {
            AuthorizationScreen(authViewModel, navController)
        }
        composable(NavigationRoutes.CREATE_PIN) {
            CreatePinScreen(navController, authViewModel, true)
        }
        composable(NavigationRoutes.INPUT_PIN) {
            CreatePinScreen(navController, authViewModel, false)
        }
        composable(NavigationRoutes.INPUT_CODE){
            InputEmailCodeScreen(authViewModel, navController)
        }
        composable(NavigationRoutes.CREATE_PASS) {
            CreatePasswordScreen(authViewModel, navController)
        }
        composable(NavigationRoutes.CREATE_USER) {
            CreateProfileScreen(navController, authViewModel)
        }
        composable(NavigationRoutes.MAIN){
            MainScreen(mainViewModel, navController)
        }
        composable(NavigationRoutes.CATALOG) {
            CatalogScreen(mainViewModel, navController)
        }
        composable(NavigationRoutes.CART) {
            CartScreen(mainViewModel, navController)
        }
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen(mainViewModel,navController)
        }
        composable(NavigationRoutes.ORDER_LIST) {
            ListOrder(navController,mainViewModel)
        }
    }
}