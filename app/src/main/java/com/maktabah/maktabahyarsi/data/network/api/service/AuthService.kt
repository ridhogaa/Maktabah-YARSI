package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registerRequestBody: RegisterRequestBody): RegisterResponse

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponse

}