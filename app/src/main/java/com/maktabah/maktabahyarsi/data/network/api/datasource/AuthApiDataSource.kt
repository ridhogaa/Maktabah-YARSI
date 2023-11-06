package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.network.api.service.AuthService
import retrofit2.http.Body
import javax.inject.Inject


interface AuthApiDataSource {
    suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse
}

class AuthApiDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthApiDataSource {
    override suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse =
        authService.register(registerRequestBody)

    override suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse =
        authService.login(loginRequestBody)

}