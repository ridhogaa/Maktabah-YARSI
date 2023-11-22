package com.maktabah.maktabahyarsi.data.network.api.model.book

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.maktabah.maktabahyarsi.data.network.api.model.category.DataItemCategory
import com.maktabah.maktabahyarsi.data.network.api.model.category.DataItemSubCategory

@Keep
data class GetBookResponse(

    @field:SerializedName("data")
    val data: List<DataItemBook>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("statusCode")
    val statusCode: Int
)

@Keep
data class DataItemBook(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("creator")
    val creator: String,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("contributor")
    val contributor: String,

    @field:SerializedName("date_created")
    val dateCreated: String,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("source")
    val source: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("category")
    val category: List<DataItemCategory>,

    @field:SerializedName("sub_category")
    val subCategory: List<DataItemSubCategory>
)
