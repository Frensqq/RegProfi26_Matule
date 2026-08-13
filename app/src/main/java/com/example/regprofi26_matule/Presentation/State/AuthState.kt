package com.example.regprofi26_matule.Presentation.State

import com.example.netlibrary.domain.model.User
import com.example.netlibrary.domain.model.changePassword.OTPAuthResponse
import com.example.netlibrary.domain.model.changePassword.ResponseOtp

data class AuthState (
    var isLoading:Boolean = false,
    var isSuccess:Boolean =false,
    var error: String?=null,
    var isNotInternet:Boolean = false,


    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = password,

    var user: User? =  null,

    val name: String= "",
    val surname: String = "",
    val patronymic: String = "",
    val tg: String = "",
    val dateUser: String = "",
    val gender: String = "",
    val phone: String = "",


    var pinCode: String = "",

    var responseOtp: ResponseOtp? = null,
    var responseAuthOtp: OTPAuthResponse? = null

)