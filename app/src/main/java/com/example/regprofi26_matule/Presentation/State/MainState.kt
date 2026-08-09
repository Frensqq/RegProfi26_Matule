package com.example.regprofi26_matule.Presentation.State

import com.example.netlibrary.domain.model.ResponseProducts
import com.example.netlibrary.domain.model.ResponsesNews

data class MainState(
    var isLoading:Boolean = false,
    var isSuccess:Boolean =false,
    var error: String?=null,
    var isNotInternet:Boolean = false,

    var searchString: String = "",

    var News: ResponsesNews? = null,
    val categoryList: List<String> = listOf("Популярные", "Мужчинам", "Женщинам", "Аксессуары"),
    var currentCategory: String = categoryList[0],

    var searchFilter: String? = null,
    var Products: ResponseProducts? = null,

    val tabBarState: String = "Главная"

    )
