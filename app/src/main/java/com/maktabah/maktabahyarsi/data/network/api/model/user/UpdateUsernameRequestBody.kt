package com.maktabah.maktabahyarsi.data.network.api.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class UpdateUsernameRequestBody(
    @field:SerializedName("username") val username: String,
)