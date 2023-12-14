package com.maktabah.maktabahyarsi.data.network.api.model.book

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GetContentResponse(
    @SerializedName("data")
    val data: ContentData,

    @SerializedName("status")
    val status: String,

    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("message")
    val message: String
)

@Keep
data class ContentData(
    @SerializedName("heading")
    val heading: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("listcontent")
    val listContent: String,

    @SerializedName("page")
    val page: Int
)
