package com.example.regprofi26_matule.Presentation.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.netlibrary.data.remote.PBApiServis
import com.example.netlibrary.domain.model.NetworkResult
import com.example.netlibrary.domain.model.RequestCart
import com.example.netlibrary.domain.model.RequestOrder
import com.example.netlibrary.domain.model.RequestProject
import com.example.regprofi26_matule.Domain.UseCase
import com.example.regprofi26_matule.Domain.Repository.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.State.MainState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.io.copyTo

class MainViewModel(
    private val UseCase: UseCase,
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: MainState get() = _state.value

    fun updateState(newState: MainState) {
        _state.value = newState
    }



    fun getUser( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                PBApiServis.token = userRepository.token
                when(val response = UseCase.getUser(
                    userRepository.userId
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                User = response.data,
                                isLoading = false
                            )
                        )
                        Log.d("getUser", response.data.id)
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getUser Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getUser NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getUser ViewModel", e.message.toString())
            }
        }
    }
    fun getProject( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.getProject(
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                Projects = response.data,
                                isLoading = false
                            )
                        )
                        Log.d("getProject", response.data.totalItems.toString())
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getProject Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getProject NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getProject ViewModel", e.message.toString())
            }
        }
    }
    fun postProject( navController: NavHostController){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.postProject(
                    userRepository.token,
                    RequestProject(
                        state.titleProject,
                        typeProject = state.typeProject,
                        userRepository.userId,
                        dateStart = state.dateStart,
                        dateEnd = state.dateEnd,
                        gender = state.genderProject,
                        description_source = state.description_source,
                        category = state.categoryProject,
                        selectedImageFile
                    )
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                Project = response.data,
                                isLoading = false
                            )
                        )
                        navController.navigate(NavigationRoutes.PROJECT)
                        Log.d("postProject", response.data.id)
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("postProject Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("postProject NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("postProject ViewModel", e.message.toString())
            }
        }
    }

    fun getOrder( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.getOrders(
                    "user_id = '${userRepository.userId}'"
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                Orders = response.data,
                                isLoading = false
                            )
                        )

                        Log.d("getOrder", response.data.totalItems.toString())
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getOrder Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getOrder NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getOrder ViewModel", e.message.toString())
            }
        }
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
                        if ((state.currentCategory == "" && state.searchString == "") || state.searchFilter == null){
                            updateState(
                                state.copy(
                                    totalProducts = response.data,
                                    isLoading = false
                                )
                            )
                        }
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

    fun getCart( ){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.getCart(
                    "user_id = '${userRepository.userId}'"
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                Cart = response.data,
                                isLoading = false
                            )
                        )
                        Log.d("getCart", response.data.totalItems.toString())
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("getCart Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("getCart NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("getCart ViewModel", e.message.toString())
            }
        }
    }

    fun deleteCart(id: String){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.deleteCart(
                    id
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                isLoading = false
                            )
                        )
                        getCart()
                        Log.d("deleteCart", "Deleted")
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("deleteCart Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("deleteCart NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("deleteCart ViewModel", e.message.toString())
            }
        }
    }
    fun postCart(productId: String){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.postBucket(
                    RequestCart(
                        userRepository.userId,
                        productId,
                        1
                    )
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                isLoading = false
                            )
                        )
                        getCart()
                        Log.d("postCart", response.data.id)
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("postCart Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("postCart NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("postCart ViewModel", e.message.toString())
            }
        }
    }
    fun patchCart(){
        viewModelScope.launch {
            updateState(state.copy(isLoading = true, error = null))
            try {
                when(val response = UseCase.patchBucket(
                    state.currentCartId,
                    RequestCart(
                        userRepository.userId,
                        state.currentProductId,
                        state.countProduct
                    )
                )){
                    is NetworkResult.Success -> {
                        updateState(
                            state.copy(
                                isLoading = false
                            )
                        )
                        getCart()
                        Log.d("patchCart", response.data.id)
                    }
                    is NetworkResult.Error ->{
                        updateState(state.copy(isLoading = false, error = response.errorResponse.message))
                        Log.e("patchCart Error", response.errorResponse.message)
                    }
                    is NetworkResult.NoInternet -> {
                        updateState(state.copy(isNotInternet = true))
                        Log.e("patchCart NoInternet", state.error.toString())
                    }

                }
            }catch (e: Exception){
                Log.e("patchCart ViewModel", e.message.toString())
            }
        }
    }

    fun postOrder(
        productId: String,
        count: Int
    ) {
        viewModelScope.launch {

            updateState(
                state.copy(
                    isLoading = true,
                    error = null
                )
            )

            try {

                when (
                    val response = UseCase.postOrder(
                        RequestOrder(
                            userRepository.userId,
                            productId,
                            count
                        )
                    )
                ) {

                    is NetworkResult.Success -> {

                        updateState(
                            state.copy(
                                isLoading = false,
                                ResponseOrders = state.ResponseOrders + response.data,
                                ResponseOrder = response.data
                            )
                        )

                        Log.d(
                            "postOrder",
                            response.data.id
                        )
                    }

                    is NetworkResult.Error -> {
                        updateState(
                            state.copy(
                                isLoading = false,
                                error = response.errorResponse.message
                            )
                        )
                    }

                    is NetworkResult.NoInternet -> {
                        updateState(
                            state.copy(
                                isLoading = false,
                                isNotInternet = true
                            )
                        )
                        Log.e("postOrder NoInternet", state.error.toString())
                    }
                }

            } catch (e: Exception) {
                Log.e(
                    "postOrder ViewModel",
                    e.message.toString()
                )
            }
        }
    }

    fun getDescProduct(){
        viewModelScope.launch {

            updateState(
                state.copy(
                    isLoading = true,
                    error = null
                )
            )

            try {
                when (
                    val response = UseCase.getProduct(
                        id = state.currentProductId
                    )
                ) {

                    is NetworkResult.Success -> {

                        updateState(
                            state.copy(
                                isLoading = false,
                                Product = response.data
                            )
                        )

                        Log.d(
                            "getDescProduct",
                            response.data.id
                        )
                    }

                    is NetworkResult.Error -> {
                        updateState(
                            state.copy(
                                isLoading = false,
                                error = response.errorResponse.message
                            )
                        )
                    }

                    is NetworkResult.NoInternet -> {
                        updateState(
                            state.copy(
                                isLoading = false,
                                isNotInternet = true
                            )
                        )
                        Log.e("getDescProduct NoInternet", state.error.toString())
                    }
                }

            } catch (e: Exception) {
                Log.e(
                    "getDescProduct ViewModel",
                    e.message.toString()
                )
            }
        }
    }

    var selectedImageUri by mutableStateOf<Uri?>(null)
    var selectedImageFile by mutableStateOf<File?>(null)
    var selectedImageName by mutableStateOf("")

    fun selectImage(uri: Uri, context: Context) {
        selectedImageUri = uri
        selectedImageName = getFileNameFromUri(context, uri)
        selectedImageFile = uriToFile(context, uri)
    }

    fun clearSelectedImage() {
        selectedImageUri = null
        selectedImageFile = null
        selectedImageName = ""
    }

    private fun getFileNameFromUri(context: Context, uri: Uri): String {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex) ?: "image_${System.currentTimeMillis()}.jpg"
        } ?: "image_${System.currentTimeMillis()}.jpg"
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e("MainViewModel", "Failed to open input stream for URI: $uri")
                return null
            }

            val fileName = "avatar_${System.currentTimeMillis()}.jpg"
            val tempFile = File(context.cacheDir, fileName)

            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            inputStream.close()

            Log.d("MainViewModel", "File created: ${tempFile.absolutePath}")
            Log.d("MainViewModel", "File size: ${tempFile.length()} bytes")

            tempFile
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error converting URI to file: ${e.message}", e)
            null
        }
    }



    fun getImageUrl(collectionId: String, recordId: String, fileName: String): String{
        val url = UseCase.getImageUrl(collectionId,recordId,fileName)
        Log.d("getNewsImage ViewModel", url)
        return url
    }


    fun checkNotificationEnabled(): Boolean{
        return userRepository.notification
    }

    fun setNotification(newValue: Boolean){
        userRepository.notification = newValue
    }

    fun logout(){
        userRepository.clear()
    }

}