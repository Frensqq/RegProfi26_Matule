package com.example.regprofi26_matule.Presentation.Screen.Market

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.Screen.Component.CalculateCostInCart
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Card.CardCart
import com.example.uikit.Card.OrderCardItem
import com.example.uikit.Card.OrderCardOpen
import com.example.uikit.Header.Header
import com.example.uikit.Header.HeaderSmall
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography
import kotlinx.coroutines.delay
import java.nio.file.WatchEvent

@Composable
fun CartScreen(viewModel: MainViewModel, navController: NavHostController){


    var StateOrderCreate by remember { mutableStateOf(false) }
    val state = viewModel.state
    var launch by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (launch){
            viewModel.updateState(state.copy(searchFilter = null))
            viewModel.getCart()
            viewModel.getProducts()
            launch = false
        }
    }


    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        SpacerH(60)

        HeaderSmall({
            navController.navigate(NavigationRoutes.CATALOG)
        }, {
            state.Cart?.items?.forEach {
                viewModel.deleteCart(it.id)
            }
        }, "Корзина")

        SpacerH(32)

        if (state.Cart != null && state.Cart!!.totalItems > 0){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
                items(state.Cart!!.items){item ->
                    val data = state.totalProducts?.items?.find {  it.id == item.product_id}
                    CardCart(data!!.title?: "",
                        item.count.toString(),
                        data.price.toString(),
                        {count->
                            viewModel.updateState(state.copy(
                                currentCartId = item.id,
                                currentProductId = item.product_id,
                                countProduct = count
                            ))
                            viewModel.patchCart()
                        },
                        {
                            viewModel.deleteCart(item.id)
                        }
                    )
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                        Text(
                            "Сумма",
                            style = createMatuleTypography().title2Semibold,
                            )

                        Text(
                            "${CalculateCostInCart(viewModel)} ₽",
                                    style = createMatuleTypography().title2Semibold,
                        )

                    }

                    SpacerH(120)
                }

            }
        }
        else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "КОРЗИНА ПУСТА",
                    modifier = Modifier.padding(bottom = 120.dp),
                    style = createMatuleTypography().headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

    }

    Box(modifier = Modifier
        .padding(
            horizontal = 20.dp,
            vertical = 32.dp)
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        ButtonBig(text =
            if (state.Cart != null && state.Cart!!.totalItems > 0) "Перейти к оформлению заказа"
            else "Выбрать товары",{
            if (state.Cart != null && state.Cart!!.totalItems > 0) {

                val orderItems = state.Cart!!.items.map { cartItem ->

                    val product = state.totalProducts!!.items.find {
                        it.id == cartItem.product_id
                    }!!

                    OrderCardItem(
                        title = product.title,
                        count = cartItem.count,
                        cost = product.price
                    )
                }

                // Записываем ВЕСЬ список одним обновлением
                viewModel.updateState(
                    state.copy(
                        OrderItemList = orderItems
                    )
                )

                // Создаём отдельный заказ для каждого товара
                state.Cart!!.items.forEach { cartItem ->
                    viewModel.postOrder(
                        cartItem.product_id,
                        cartItem.count
                    )
                }

                StateOrderCreate = true

            } else {
                navController.navigate(NavigationRoutes.CATALOG)
            }
        },true
        )

    }

if (StateOrderCreate && state.ResponseOrder != null ) {

    Box(
        Modifier.fillMaxSize()
            .background(MatuleTheme.colors.black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {


        OrderCardOpen(
            state.ResponseOrder!!.id,
            CalculateCostInCart(viewModel).toString(),
            date = state.ResponseOrder!!.created,
            "Оплачен",
            {},
            state.OrderItemList,
            {}, {}, {
                viewModel.updateState(
                    state.copy(OrderItemList = emptyList())
                )
                StateOrderCreate = false
            }

        )

    }
}



}