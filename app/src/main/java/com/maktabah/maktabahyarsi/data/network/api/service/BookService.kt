package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BookService {

    @GET("/api/v1/bibliographies")
    suspend fun getBooks(
        @Query("sort") sort: String? = null,
    ): GetBookResponse

    @GET("/api/v1/bibliographies/{id}")
    suspend fun getBookById(
        @Path("id") id: String
    ): GetBookByIdResponse

    @GET("/api/v1/bibliographies/category/{id}")
    suspend fun getBooksByCategoryId(
        @Path("id") id: String
    ): GetBookResponse

    @GET("/api/v1/listcontents/{id}")
    suspend fun getContentsBook(
        @Path("id") id: String
    ): GetListContentBookResponse

    @GET("/api/v1/contents/{id}")
    suspend fun getContents(
        @Path("id") id: String
    ): GetContentResponse
}