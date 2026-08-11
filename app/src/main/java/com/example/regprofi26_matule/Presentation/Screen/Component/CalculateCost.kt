package com.example.regprofi26_matule.Presentation.Screen.Component

import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel

fun CalculateCostInCart(viewModel: MainViewModel): Int{

    val state = viewModel.state
    var totalCost = 0

    state.totalProducts?.items?.forEach { item ->

        val count = state.Cart?.items?.find { it.product_id == item.id }?.count?: 0
        totalCost += item.price * count

    }
    return  totalCost
}