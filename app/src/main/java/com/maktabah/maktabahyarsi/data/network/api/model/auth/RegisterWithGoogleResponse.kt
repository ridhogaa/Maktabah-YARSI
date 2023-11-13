package com.maktabah.maktabahyarsi.data.network.api.model.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterWithGoogleResponse(

    @field:SerializedName("data")
    val data: DataRegisterWithGoogle,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("status")
    val status: String
)

@Keep
data class DataRegisterWithGoogle(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
