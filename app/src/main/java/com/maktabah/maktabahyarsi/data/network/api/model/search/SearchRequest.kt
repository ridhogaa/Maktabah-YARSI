package com.maktabah.maktabahyarsi.data.network.api.model.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchRequest(
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("value")
    val value: String,
    @field:SerializedName("index")
    val index: String,
    @field:SerializedName("wild_card")
    val wild_card: Boolean
)

