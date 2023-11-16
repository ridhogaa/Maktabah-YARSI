package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface



BookService {

    @GET("/api/v1/bibliografi")
    suspend fun getBooks(
        @Query("sort") sort: String? = null,
        @Query("id") id: String? = null,
        @Query("category") category: String? = null,
    ): GetBookResponse

}