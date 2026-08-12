package com.example.regprofi26_matule.Presentation.Screen.Profile

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Presentation.Screen.Component.CalculateCostInCart
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Card.CardCart
import com.example.uikit.Card.OrderCard
import com.example.uikit.Card.OrderCardItem
import com.example.uikit.Card.OrderCardOpen
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.createMatuleTypography

@Composable
fun ListOrder(navController: NavHostController, viewModel: MainViewModel){


    val state = viewModel.state
    var launch by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (launch){
            viewModel.getOrder()
            launch = false
        }
    }
    Column(Modifier.padding(horizontal = 20.dp)){
    SpacerH(72)

        Text("Список заказов", style = createMatuleTypography().title1Heavy)


        SpacerH(20)

    if (state.Orders != null && state.Orders!!.totalItems > 0){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(state.Orders!!.items){item ->
                val data = state.totalProducts?.items?.find {  it.id == item.product_id}
                var openCard by remember { mutableStateOf(false) }
                if (!openCard){
                    OrderCard(
                        item.id,
                        data?.price.toString() ?:"",
                        item.created,
                        "Оплачен"
                    ) {
                        openCard = true
                    }
                }
                else{
                    OrderCardOpen(
                        item.id,
                        data?.price.toString() ?:"",
                        item.created,
                        "Оплачен",
                        listOrder = listOf(OrderCardItem(
                            title = data?.title?:"Error loading",
                            count = item.count,
                            cost = data?.price?: 0
                        )),
                        help = {},
                        checkClick = {},
                        exit = {
                            openCard =false
                        },
                        onClick = {
                            openCard =false
                        }
                    )
                }

            }
        }
    }
    }

}