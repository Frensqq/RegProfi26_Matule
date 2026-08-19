package com.example.regprofi26_matule.Data.Repository

import android.content.Context
import android.content.SharedPreferences
import com.example.regprofi26_matule.Domain.Repository.UserRepository

class UserRepositoryImpl(
    context: Context
) : UserRepository {

    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            "actSystem",
            Context.MODE_PRIVATE
        )

    override var act: Boolean
        get() = preferences.getBoolean("Act", false)
        set(value) {
            preferences.edit()
                .putBoolean("Act", value)
                .apply()
        }

    override var notification: Boolean
        get() = preferences.getBoolean("Notification", false)
        set(value) {
            preferences.edit()
                .putBoolean("Notification", value)
                .apply()
        }

    override var userId: String
        get() = preferences.getString("UserId", "") ?: ""
        set(value) {
            preferences.edit()
                .putString("UserId", value)
                .apply()
        }

    override var token: String
        get() = preferences.getString("Token", "") ?: ""
        set(value) {
            preferences.edit()
                .putString("Token", value)
                .apply()
        }

    override var email: String
        get() = preferences.getString("Email", "") ?: ""
        set(value) {
            preferences.edit()
                .putString("Email", value)
                .apply()
        }

    override var pin: String
        get() = preferences.getString("pin_code", "") ?: ""
        set(value) {
            preferences.edit()
                .putString("pin_code", value)
                .apply()
        }

    override fun clear() {
        preferences.edit()
            .clear()
            .apply()
    }
}