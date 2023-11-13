package com.maktabah.maktabahyarsi.data.network.api.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetSubCategoryResponse(

    @field:SerializedName("data")
    val data: List<DataItemSubCategory>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("statusCode")
    val statusCode: Int
)

@Keep
data class DataItemSubCategory(

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("category")
    val category: String
)
