package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdatePasswordRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUserResponse
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUsernameRequestBody
import com.maktabah.maktabahyarsi.data.network.api.service.UserService
import javax.inject.Inject


interface UserApiDataSource {
    suspend fun getUserById(token: String, id: String): GetUserByIdResponse
    suspend fun updateUsernameUserById(
        token: String,
        id: String,
        updateUsernameRequestBody: UpdateUsernameRequestBody
    ): UpdateUserResponse

    suspend fun updatePasswordUserById(
        token: String,
        id: String,
        updatePasswordRequestBody: UpdatePasswordRequestBody
    ): UpdateUserResponse
}

class UserApiDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserApiDataSource {
    override suspend fun getUserById(token: String, id: String): GetUserByIdResponse =
        userService.getUserById(token, id)

    override suspend fun updateUsernameUserById(
        token: String,
        id: String,
        updateUsernameRequestBody: UpdateUsernameRequestBody
    ): UpdateUserResponse =
        userService.updateUsernameUserById(token, id, updateUsernameRequestBody)

    override suspend fun updatePasswordUserById(
        token: String,
        id: String,
        updatePasswordRequestBody: UpdatePasswordRequestBody
    ): UpdateUserResponse =
        userService.updatePasswordUserById(token, id, updatePasswordRequestBody)

}