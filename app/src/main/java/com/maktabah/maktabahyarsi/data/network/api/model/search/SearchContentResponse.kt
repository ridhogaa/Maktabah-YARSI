package com.maktabah.maktabahyarsi.data.network.api.model.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchContentResponse(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("statusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("_index")
        val index: String = "",
        @SerializedName("_id")
        val id: String = "",
        @SerializedName("_score")
        val score: Int = 0,
        @SerializedName("_ignored")
        val ignored: List<String> = listOf(),
        @SerializedName("_source")
        val source: Source = Source()
    ) {
        data class Source(
            @SerializedName("id_content")
            val idContent: String = "",
            @SerializedName("author")
            val author: String = "",
            @SerializedName("bibliography_title")
            val bibliographyTitle: String = "",
            @SerializedName("id_bibliography")
            val idBibliography: String = "",
            @SerializedName("text")
            val text: String = "",
            @SerializedName("page")
            val page: Int = 0,
            @SerializedName("heading")
            val heading: String = ""
        )
    }
}

data class Source(
    val idContent: String,
    val author: String,
    val bibliographyTitle: String,
    val idBibliography: String,
    val text: String,
    val page: Int,
    val heading: String
)

fun SearchContentResponse.mapToEntityList(): List<Source> {
    val dataList: MutableList<Source> = mutableListOf()
    dataList.clear()
    data.forEach { data ->
        dataList.add(
            Source(
                data.source.idContent,
                data.source.author,
                data.source.bibliographyTitle,
                data.source.idBibliography,
                data.source.text,
                data.source.page,
                data.source.heading,
            )
        )
    }
    return dataList
}