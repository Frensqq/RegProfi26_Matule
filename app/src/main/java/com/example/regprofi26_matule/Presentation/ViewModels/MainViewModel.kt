package com.example.regprofi26_matule.Presentation.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.netlibrary.data.remote.PBApi
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
import com.example.regprofi26_matule.Presentation.State.MainState
import kotlinx.coroutines.launch

class MainViewModel(private val UseCase: UseCase): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: MainState get() = _state.value

    fun updateState(newState: MainState) {
        _state.value = newState
    }

    fun getImageUrl(collectionId: String, recordId: String, fileName: String): String{
        val url = UseCase.getImageUrl(collectionId,recordId,fileName)
        Log.d("getNewsImage ViewModel", url)
        return url
    }

    fun getNews( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.getNews()){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                News = response.data,
                                isLoading = false
                            )
                        )
                        Log.d("getNews", response.data.totalItems.toString())
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getNews Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getNews NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getNews ViewModel", e.message.toString())
            }
        }
    }

    fun getProducts( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.getProducts(
                    state.searchFilter
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                Products = response.data,
                                isLoading = false
                            )
                        )
                        Log.d("getProducts", response.data.totalItems.toString())
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getProducts Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getProducts NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getProducts ViewModel", e.message.toString())
            }
        }
    }


}