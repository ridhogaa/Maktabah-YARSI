package com.maktabah.maktabahyarsi.data.network.api.model.error

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BaseErrorResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
