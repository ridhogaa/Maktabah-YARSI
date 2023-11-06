package com.maktabah.maktabahyarsi.data.network.api.model.auth

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterRequestBody(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
