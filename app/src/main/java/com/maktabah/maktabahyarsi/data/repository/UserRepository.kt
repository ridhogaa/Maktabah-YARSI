package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.UserApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface UserRepository {
    suspend fun getUserById(id: String): Flow<ResultWrapper<GetUserByIdResponse>>
}

class UserRepositoryImpl @Inject constructor(
    private val userApiDataSource: UserApiDataSource
) : UserRepository {
    override suspend fun getUserById(id: String): Flow<ResultWrapper<GetUserByIdResponse>> =
        proceedFlow { userApiDataSource.getUserById(id) }

}