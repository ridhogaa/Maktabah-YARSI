package com.maktabah.maktabahyarsi.data.network.api.model.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(

    @field:SerializedName("data")
    val data: DataLogin,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("status")
    val status: String
)

@Keep
data class DataLogin(
    @field:SerializedName("access_token")
    val accessToken: AccessToken,
)

@Keep
data class AccessToken(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("id")
    val id: String
)
