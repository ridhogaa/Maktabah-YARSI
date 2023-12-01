package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BookService {

    @GET("/api/v1/bibliographies")
    suspend fun getBooks(
        @Query("sort") sort: String? = null,
        @Query("id") id: String? = null,
        @Query("category") category: String? = null,
        @Query("sub_category") subCategory: String? = null,
    ): GetBookResponse

    @GET("/api/v1/listcontents/{id}")
    suspend fun getContentsBook(
        @Path("id") id: String
    ): GetListContentBookResponse
}