package com.maktabah.maktabahyarsi.data.network.api.model.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse(

	@field:SerializedName("data")
    val data: DataRegister,

	@field:SerializedName("message")
    val message: String,

	@field:SerializedName("status")
    val status: String,

	@field:SerializedName("statusCode")
    val statusCode: Int
)

@Keep
data class DataRegister(

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
