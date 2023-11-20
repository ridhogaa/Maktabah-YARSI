package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface UserService {

    @GET("/api/v1/users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetUserByIdResponse

}