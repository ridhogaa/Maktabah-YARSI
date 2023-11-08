package com.maktabah.maktabahyarsi.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetUserByIdResponse(

    @field:SerializedName("data")
    val data: DataUserById,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("status")
    val status: String
)

@Keep
data class DataUserById(

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
