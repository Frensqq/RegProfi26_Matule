package com.example.regprofi26_matule.Domain.Repository

import android.content.Context
import android.content.SharedPreferences

interface UserRepository {

    var act: Boolean

    var notification: Boolean

    var userId: String

    var token: String

    var email: String

    var pin: String

    fun clear()
}