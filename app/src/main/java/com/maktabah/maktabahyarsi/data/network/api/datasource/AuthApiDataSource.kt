package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginWithGoogleResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterWithGoogleResponse
import com.maktabah.maktabahyarsi.data.network.api.service.AuthService
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject


interface AuthApiDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse
    suspend fun registerWithGoogle(token: JsonObject): RegisterWithGoogleResponse
    suspend fun loginWithGoogle(token: JsonObject): LoginWithGoogleResponse
}

class AuthApiDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthApiDataSource {
    override suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse =
        authService.register(registerRequestBody)

    override suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse =
        authService.login(loginRequestBody)

    override suspend fun registerWithGoogle(token: JsonObject): RegisterWithGoogleResponse =
        authService.registerWithGoogle(token)

    override suspend fun loginWithGoogle(token: JsonObject): LoginWithGoogleResponse =
        authService.loginWithGoogle(token)

}