package com.maktabah.maktabahyarsi.data.network.api.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetCategoryResponse(

    @field:SerializedName("data")
    val data: List<DataItemCategory>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("status")
    val status: String
)

@Keep
data class DataItemCategory(

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("subcategories")
    val subcategories: List<SubcategoriesItem>
)

@Keep
data class SubcategoriesItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("total")
    val total: Int,
)
