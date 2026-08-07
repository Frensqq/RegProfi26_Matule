package com.example.regprofi26_matule.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation(isOnline: Boolean){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH){

        composable(NavigationRoutes.SPLASH){

        }
        composable(NavigationRoutes.AUTH) {

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