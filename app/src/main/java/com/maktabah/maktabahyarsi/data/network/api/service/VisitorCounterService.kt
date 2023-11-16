package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.visitor.GetVisitorCounterResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query


interface VisitorCounterService {
    @GET("/api/v1/visitor")
    suspend fun getVisitorCounter(
        @Query("month") month: String,
        @Query("year") year: String
    ): GetVisitorCounterResponse

    @PATCH("/api/v1/visitor")
    suspend fun updateVisitorCounter()
}