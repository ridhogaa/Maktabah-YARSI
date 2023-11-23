package com.maktabah.maktabahyarsi.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class UpdatePasswordRequestBody(

    @field:SerializedName("old_password")
    val oldPassword: String,

    @field:SerializedName("password")
    val password: String,
)