package com.maktabah.maktabahyarsi.data.network.api.model.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginWithGoogleResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
)

@Keep
data class Data(

    @field:SerializedName("access_token")
    val accessToken: AccessTokenGoogle? = null
)

@Keep
data class AccessTokenGoogle(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
