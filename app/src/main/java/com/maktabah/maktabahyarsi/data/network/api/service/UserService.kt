package com.maktabah.maktabahyarsi.data.network.api.service

import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdatePasswordRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUserResponse
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUsernameRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path


interface UserService {

    @GET("/api/v1/users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetUserByIdResponse

    @PATCH("/api/v1/users/{id}")
    suspend fun updateUsernameUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateUsernameRequestBody: UpdateUsernameRequestBody,
    ): UpdateUserResponse

    @PATCH("/api/v1/users/{id}")
    suspend fun updatePasswordUserById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updatePasswordRequestBody: UpdatePasswordRequestBody
    ): UpdateUserResponse

}