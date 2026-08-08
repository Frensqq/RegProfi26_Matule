package com.example.regprofi26_matule.Presentation.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.regprofi26_matule.Presentation.State.AuthState

class AuthViewModel(): ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state: AuthState get() = _state.value

    fun updateState(newState: AuthState) {
        _state.value = newState
    }

}