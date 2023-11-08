package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface UserService {

    @GET("/api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: String): GetUserByIdResponse

}