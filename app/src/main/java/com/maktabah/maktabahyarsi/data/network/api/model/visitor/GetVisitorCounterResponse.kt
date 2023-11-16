package com.maktabah.maktabahyarsi.data.network.api.model.visitor

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetVisitorCounterResponse(

    @field:SerializedName("data")
    val data: List<DataItemVisitor?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
)

@Keep
data class DataItemVisitor(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("month")
    val month: String? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("range")
    val range: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null
)
