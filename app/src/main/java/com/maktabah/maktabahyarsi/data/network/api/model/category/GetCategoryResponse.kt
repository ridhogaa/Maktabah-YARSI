package com.maktabah.maktabahyarsi.data.network.api.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.maktabah.maktabahyarsi.data.network.api.model.category.sub.SubcategoriesItem

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
data class SubcategoriesItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("subcategories")
    val subcategories: List<SubcategoriesItem>
)

@Keep
data class DataItemCategory(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("subcategories")
    val subcategories: List<SubcategoriesItem>
)
