package com.maktabah.maktabahyarsi.data.network.api.model.category.sub

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetSubCategoryResponse(

    @field:SerializedName("data")
    val data: DataSubCategory,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("statusCode")
    val statusCode: Int
)

@Keep
data class SubcategoriesItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("category")
    val category: String
)

@Keep
data class DataSubCategory(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("subcategories")
    val subcategories: List<SubcategoriesItem>
)
