package com.example.regprofi26_matule.Presentation.State

import com.example.netlibrary.domain.model.Product
import com.example.netlibrary.domain.model.Project
import com.example.netlibrary.domain.model.ResponseCarts
import com.example.netlibrary.domain.model.ResponseOrder
import com.example.netlibrary.domain.model.ResponseProducts
import com.example.netlibrary.domain.model.ResponsesNews
import com.example.netlibrary.domain.model.ResponsesProject
import com.example.netlibrary.domain.model.User
import com.example.uikit.Card.OrderCardItem
import java.io.File

data class MainState(
    var isLoading:Boolean = false,
    var isSuccess:Boolean =false,
    var error: String?=null,
    var isNotInternet:Boolean = false,

    var searchString: String = "",

    var News: ResponsesNews? = null,
    val categoryList: List<String> = listOf("Популярные", "Мужчинам", "Женщинам", "Аксессуары"),
    var currentCategory: String = "",

    var searchFilter: String? = null,
    var Products: ResponseProducts? = null,
    var totalProducts: ResponseProducts? = null,

    val tabBarState: String = "Главная",

    var Cart: ResponseCarts? = null,
    var currentProductId: String = "",
    var currentCartId: String = "",
    var countProduct: Int = 1,

    var ResponseOrder: ResponseOrder? = null,
    var ResponseOrders: List<ResponseOrder> = emptyList(),
    var OrderItemList: List<OrderCardItem>  = emptyList(),
    var stateOrderCreate: Boolean = false,

    var Product: Product? = null,
    val User: User? = null,
    val Orders: ResponseCarts ? = null,

    var Projects: ResponsesProject? = null,
    var Project: Project? = null,

    var titleProject: String = "",
    var genderProject: String = "",
    var description_source: String = "",
    var categoryListProject: List<String> = listOf("XS","S","M","L","XL","XXL"),
    var categoryProject: String = "",
    var image: File? = null,
    var typeProject: String = "",
    var typeListProject: List<String> = listOf("Рубаха","Футболка","Штаны","Шорты","Кофта","Аксессуар"),
    var dateStart: String = "",
    var dateEnd: String = ""
    )
