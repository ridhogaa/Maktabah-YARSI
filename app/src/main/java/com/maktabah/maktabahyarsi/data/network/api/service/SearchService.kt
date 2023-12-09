package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface SearchService {

    @POST("/api/v1/search")
    suspend fun searchContents(@Body searchRequest: SearchRequest): SearchContentResponse

    @GET("/api/v1/bibliographies")
    suspend fun searchBooks(@Query("title") title: String): GetBookResponse

}