package com.maktabah.maktabahyarsi.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateUserResponse(

    @field:SerializedName("data")
    val data: DataUpdateUserResponse? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
)

@Keep
data class DataUpdateUserResponse(

    @field:SerializedName("upsertedId")
    val upsertedId: Any? = null,

    @field:SerializedName("upsertedCount")
    val upsertedCount: Int? = null,

    @field:SerializedName("acknowledged")
    val acknowledged: Boolean? = null,

    @field:SerializedName("modifiedCount")
    val modifiedCount: Int? = null,

    @field:SerializedName("matchedCount")
    val matchedCount: Int? = null
)
