package com.maktabah.maktabahyarsi.data.network.api.model.book

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetListContentBookResponse(

	@field:SerializedName("data")
	val data: DataItemListContent,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Keep
data class DataItemListContent(

	@field:SerializedName("sub")
	val sub: List<SubItem>,

	@field:SerializedName("bibliography")
	val bibliography: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("page")
	val page: Int
)

@Keep
data class SubItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("sub")
	val sub: List<SubItem>,

)
