package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @GET("/api/v1/contents/bibliography/{id}")
    suspend fun getContents(
        @Path("id") idBibliography: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 1,
    ): GetContentResponse

    @PATCH("/api/v1/bibliographies/{id}")
    suspend fun updateTotalReadingBook(
        @Path("id") idBibliography: String
    )
}