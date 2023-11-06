package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.AuthApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.utils.ResultWrapper
import com.maktabah.maktabahyarsi.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AuthRepository {
    suspend fun register(registerRequestBody: RegisterRequestBody): Flow<ResultWrapper<RegisterResponse>>
    suspend fun login(loginRequestBody: LoginRequestBody): Flow<ResultWrapper<LoginResponse>>
}

class AuthRepositoryImpl @Inject constructor(
    private val authApiDataSource: AuthApiDataSource
) : AuthRepository {
    override suspend fun register(registerRequestBody: RegisterRequestBody): Flow<ResultWrapper<RegisterResponse>> =
        proceedFlow { authApiDataSource.register(registerRequestBody) }

    override suspend fun login(loginRequestBody: LoginRequestBody): Flow<ResultWrapper<LoginResponse>> =
        proceedFlow { authApiDataSource.login(loginRequestBody) }

}