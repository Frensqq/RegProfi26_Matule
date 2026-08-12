package com.example.regprofi26_matule.Presentation.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.netlibrary.data.remote.PBApiServis
import com.example.netlibrary.domain.model.NetworkResult
import com.example.netlibrary.domain.model.RequestAuth
import com.example.netlibrary.domain.model.RequestRegister
import com.example.netlibrary.domain.model.RequestUser
import com.example.netlibrary.domain.model.User
import com.example.regprofi26_matule.Domain.UseCase
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.State.AuthState
import kotlinx.coroutines.launch

class AuthViewModel(private val UseCase: UseCase): ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state: AuthState get() = _state.value

    fun updateState(newState: AuthState) {
        _state.value = newState
    }


    fun Registration(navController: NavHostController){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.postUser(
                    data = RequestRegister(
                        email = state.email,
                        password = state.password,
                        passwordConfirm = state.password
                    )
                )){
                    is NetworkResult.Success -> {
                        UserRepository.UserId = response.data.id
                        UserRepository.Email = state.email
                        Auth(navController, false) // необходимо для получения токена
                        Log.d("Reg", "Success registration")
                    }
                    is NetworkResult.Error -> {
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("Reg Error", response.errorResponse.message)

                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("Reg NoInternet", state.error.toString())
                    }

                }
                Log.d("Reg", state.error.toString())
            }
            catch (e: Exception){
                Log.e("Reg ViewModel", e.message.toString())
            }
        }
    }

    fun Auth(navController: NavHostController, isAuth: Boolean){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.authUser(
                    data = RequestAuth(
                        identity = state.email,
                        password = state.password
                    )
                )){
                    is NetworkResult.Success -> {
                        UserRepository.Act = true
                        UserRepository.Token = response.data.token
                        PBApiServis.token = UserRepository.Token
                        if(isAuth){
                            navController.navigate(NavigationRoutes.CREATE_PIN)
                        }
                        else{
                            navController.navigate(NavigationRoutes.CREATE_USER)
                        }
                        Log.d("Auth", "Success authentification")
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("Auth Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("Auth NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("Auth ViewModel", e.message.toString())
            }
        }
    }

    fun patchUser(navController: NavHostController){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                PBApiServis.token = UserRepository.Token
                when(val response = UseCase.patchUser(
                    UserRepository.UserId,
                    RequestUser(
                        email = UserRepository.Email,
                        emailVisibility = true,
                        firstname = state.name,
                        secondname = state.surname,
                        datebirthday = state.dateUser,
                        lastname = state.patronymic,
                        gender = state.gender,
                        phone = state.phone
                    )
                )){
                    is NetworkResult.Success -> {
                        state.user = response.data
                        navController.navigate(NavigationRoutes.CREATE_PIN)
                        Log.d("Patch User", "Success Patch User")
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("Patch User Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("Patch User NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("Patch User ViewModel", e.message.toString())
            }
        }
    }
}