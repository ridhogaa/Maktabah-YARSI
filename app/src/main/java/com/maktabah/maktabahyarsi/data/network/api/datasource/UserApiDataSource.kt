package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.service.UserService
import javax.inject.Inject


interface UserApiDataSource {
    suspend fun getUserById(id: String): GetUserByIdResponse
}

class UserApiDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserApiDataSource {
    override suspend fun getUserById(id: String): GetUserByIdResponse =
        userService.getUserById(id)

}