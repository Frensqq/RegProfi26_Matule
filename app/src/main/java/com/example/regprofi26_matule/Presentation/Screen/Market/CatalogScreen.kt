package com.example.regprofi26_matule.Presentation.Screen.Market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.Screen.Component.CalculateCostInCart
import com.example.regprofi26_matule.Presentation.Screen.Component.MainTabBar
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Buttons.ButtonCart
import com.example.uikit.Card.PrimaryCard
import com.example.uikit.CategoryMenu.CategoryMenu
import com.example.uikit.Modal.ModalWindow
import com.example.uikit.Search.Search
import com.example.uikit.Search.SearchSmall
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography

@Composable
fun CatalogScreen(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state
    var openDescription by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
            viewModel.getProducts()
            viewModel.getCart()
    }
    LaunchedEffect(state.searchString) {
        viewModel.updateState(state.copy(searchFilter = "title ~ '${state.searchString}' || description ~ '${state.searchString}'"))
        viewModel.getProducts()
    }
    LaunchedEffect(state.currentCategory) {
        viewModel.updateState(state.copy(searchFilter = "type ~ '${state.currentCategory}'"))
        viewModel.getProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        SpacerH(72)

        Search(
            state.searchString,
            "Искать описание",
            {viewModel.updateState(state.copy(searchString = it))},
            onClick = {viewModel.updateState(state.copy(searchString = ""))}
        ) {
            navController.navigate(NavigationRoutes.CART)
        }

        SpacerH(32)

        CategoryMenu(
            state.categoryList,
            state.currentCategory,
        ) {
            viewModel.updateState(state.copy(currentCategory = it))
        }

        SpacerH(24)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.Products != null) {
                items(state.Products!!.items) {

                    val result = state.Cart?.items?.find { cartItem ->
                        cartItem.product_id == it.id
                    }
                    val isResult = result != null

                    PrimaryCard(
                        it.title,
                        it.type,
                        it.price.toString(),
                        !isResult ,
                        {
                            if (isResult){
                                viewModel.deleteCart(result!!.id)
                            }
                            else {
                                viewModel.postCart(it.id)
                            }
                        },{
                            viewModel.updateState(state.copy(
                                currentProductId = it.id
                            ))
                            viewModel.getDescProduct()
                            openDescription = true
                        }, false
                    )
                }
                item { SpacerH(88) }
            }
        }

    }

    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        if (state.Cart?.totalItems != 0 && state.Cart != null){
            Box(Modifier.padding(horizontal = 20.dp)) {
                ButtonCart("В корзину", CalculateCostInCart(viewModel), {
                    navController.navigate(NavigationRoutes.CART)
                }, true)
            }
        }

        SpacerH(35)

        Box(modifier = Modifier.background(Color.White)) {
            MainTabBar(
                navController,
                NavigationRoutes.CATALOG
            )
        }

    }

    if (openDescription){

        state.Product?.let { data ->

            ModalWindow(
                data.title ?: "",
                data.description ?: "",
                data.approximateCost ?: "",
                data.price ?: 0,
                {
                    viewModel.postCart(data.id)
                }, state.Cart!!.items.find { item -> item.product_id == data.id } == null,
                { openDescription = false }
            )
        }
    }

}