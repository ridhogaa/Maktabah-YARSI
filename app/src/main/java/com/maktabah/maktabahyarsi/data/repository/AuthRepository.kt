package com.maktabah.maktabahyarsi.data.repository

import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginWithGoogleResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterWithGoogleResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject


interface AuthRepository {
    suspend fun register(registerRequestBody: RegisterRequestBody): Flow<ResultWrapper<RegisterResponse>>
    suspend fun login(loginRequestBody: LoginRequestBody): Flow<ResultWrapper<LoginResponse>>
    suspend fun registerWithGoogle(token: JsonObject): Flow<ResultWrapper<RegisterWithGoogleResponse>>
    suspend fun loginWithGoogle(token: JsonObject): Flow<ResultWrapper<LoginWithGoogleResponse>>
}

class AuthRepositoryImpl @Inject constructor(
    private val authApiDataSource: AuthApiDataSource
) : AuthRepository {
    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<ResultWrapper<RegisterResponse>> =
        proceedFlow { authApiDataSource.register(registerRequestBody) }

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<ResultWrapper<LoginResponse>> =
        proceedFlow { authApiDataSource.login(loginRequestBody) }

    override suspend fun registerWithGoogle(token: JsonObject): Flow<ResultWrapper<RegisterWithGoogleResponse>> =
        proceedFlow { authApiDataSource.registerWithGoogle(token) }

    override suspend fun loginWithGoogle(token: JsonObject): Flow<ResultWrapper<LoginWithGoogleResponse>> =
        proceedFlow { authApiDataSource.loginWithGoogle(token) }

}