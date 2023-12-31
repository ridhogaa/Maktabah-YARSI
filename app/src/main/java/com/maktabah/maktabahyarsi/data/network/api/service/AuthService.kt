package com.maktabah.maktabahyarsi.data.network.api.service

import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginWithGoogleResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterWithGoogleResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface AuthService {

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registerRequestBody: RegisterRequestBody): RegisterResponse

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponse

    @POST("/api/v1/auth/registerauth")
    suspend fun registerWithGoogle(@Body token: JsonObject): RegisterWithGoogleResponse

    @POST("/api/v1/auth/loginauth")
    suspend fun loginWithGoogle(@Body token: JsonObject): LoginWithGoogleResponse
}