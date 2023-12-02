package com.maktabah.maktabahyarsi.data.network.api.service

import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetSubCategoryByIdCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    @GET("/api/v1/category")
    suspend fun getAllCategory(): GetCategoryResponse

    @GET("/api/v1/category/sub/{id}")
    suspend fun getSubCategoryByIdCategory(@Path("id") id: String): GetSubCategoryByIdCategoryResponse
}