package com.example.regprofi26_matule.Presentation.Screen.Component

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun validPhone(phone: String): Boolean {
    return Regex("""^(\+7|8)\d{10}$""").matches(phone)
}

fun validAge(date: String): Boolean {
    return try {
        val birth = LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("dd.MM.yyyy")
        )

        !birth.plusYears(18).isAfter(LocalDate.now())
    } catch (e: Exception) {
        false
    }
}