package com.example.regprofi26_matule.Domain

import com.example.netlibrary.domain.model.NetworkResult
import com.example.netlibrary.domain.model.Product
import com.example.netlibrary.domain.model.Project
import com.example.netlibrary.domain.model.RequestAuth
import com.example.netlibrary.domain.model.RequestCart
import com.example.netlibrary.domain.model.RequestOrder
import com.example.netlibrary.domain.model.RequestProject
import com.example.netlibrary.domain.model.RequestRegister
import com.example.netlibrary.domain.model.RequestUser
import com.example.netlibrary.domain.model.ResponseAuth
import com.example.netlibrary.domain.model.ResponseCart
import com.example.netlibrary.domain.model.ResponseCarts
import com.example.netlibrary.domain.model.ResponseOrder
import com.example.netlibrary.domain.model.ResponseProducts
import com.example.netlibrary.domain.model.ResponseRegister
import com.example.netlibrary.domain.model.ResponsesNews
import com.example.netlibrary.domain.model.ResponsesProject
import com.example.netlibrary.domain.model.User
import com.example.netlibrary.domain.model.UsersAuth
import com.example.netlibrary.domain.repository.Repository

class UseCase(private val Repository: Repository) {

    suspend fun postUser(data: RequestRegister): NetworkResult<ResponseRegister>{
        return Repository.postUser(data)
    }

    suspend fun getUser(id: String): NetworkResult<User>{
        return Repository.getUser(id)
    }

    suspend fun patchUser(id: String, data: RequestUser):NetworkResult<User>{
        return Repository.patchUser(id,data)
    }


    suspend fun authUser(data: RequestAuth): NetworkResult<ResponseAuth>{
        return Repository.authUser(data)
    }

    suspend fun getToken(): NetworkResult<UsersAuth>{
        return Repository.getToken()
    }

    suspend fun deleteToken(id: String){
        Repository.deleteToken(id)
    }
    suspend fun getNews(): NetworkResult<ResponsesNews>{
        return Repository.getNews()
    }

    suspend fun getProducts(filter: String? = null): NetworkResult<ResponseProducts>{
        return Repository.getProducts(filter)
    }

    suspend fun getProduct(id: String): NetworkResult<Product>{
        return Repository.getProduct(id)
    }

    suspend fun getProject(): NetworkResult<ResponsesProject>{
        return Repository.getProject()
    }

    suspend fun postProject(token:String, data: RequestProject): NetworkResult<Project>{
        return Repository.postProject(token, data)
    }

    suspend fun postBucket(data: RequestCart): NetworkResult<ResponseCart>{
        return Repository.postBucket(data)
    }

    suspend fun patchBucket(id:String, data: RequestCart): NetworkResult<ResponseCart>{
        return Repository.patchBucket(id,data)
    }
    suspend fun postOrder(data: RequestOrder): NetworkResult<ResponseOrder>{
        return Repository.postOrder(data)
    }

    suspend fun getOrders(filter: String? = null): NetworkResult<ResponseCarts>{
        return Repository.getOrders(filter)
    }
    suspend fun getCart(filter: String? = null): NetworkResult<ResponseCarts>{
        return Repository.getBucket(filter)
    }
    suspend fun deleteCart(id: String): NetworkResult<Unit>{
        return Repository.deleteBucket(id)
    }

    fun getImageUrl(collection: String, recordId: String, fileName: String): String{
        return Repository.getImageUrl(collection = collection, id = recordId, image = fileName )
    }

}