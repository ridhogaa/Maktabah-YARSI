package com.maktabah.maktabahyarsi.data.network.api.model.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchContentResponse(
    @field:SerializedName("data")
    val `data`: List<Data>,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("statusCode")
    val statusCode: Int
) {
    data class Data(
        @field:SerializedName("_index")
        val _index: String,
        @field:SerializedName("_id")
        val _id: String,
        @field:SerializedName("_score")
        val _score: Int,
        @field:SerializedName("_source")
        val _source: Source
    ) {
        data class Source(
            @field:SerializedName("heading")
            val heading: String,
            @field:SerializedName("text")
            val text: String,
            @field:SerializedName("listcontent")
            val listcontent: String,
            @field:SerializedName("page")
            val page: Int
        )
    }
}