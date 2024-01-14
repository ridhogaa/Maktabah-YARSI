package com.maktabah.maktabahyarsi.data.network.api.model.content

import com.google.errorprone.annotations.Keep

@Keep
data class GetContentResponse(
    val message: String? = null,
    val `data`: List<Data>? = null,
    val status: String? = null,
    val statusCode: Int? = null
) {
    data class Data(
        val _id: String? = null,
        val heading: String? = null,
        val bibliography: String? = null,
        val page: Int? = null,
        var text: String? = null,
        val size: Int? = null,
        val sub: List<Sub>? = null
    ) {
        data class Sub(
            val text: String? = null,
            val heading: String? = null,
            val page: Int? = null,
            val sub: List<Sub>? = null
        )
    }
}