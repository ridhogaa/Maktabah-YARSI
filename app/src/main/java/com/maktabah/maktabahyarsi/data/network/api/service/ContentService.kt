package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetContentResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContentService {
    @GET("/api/v1/contents/{id}")
    fun getContent(@Path("id") id: String): Call<GetContentResponse>
}
