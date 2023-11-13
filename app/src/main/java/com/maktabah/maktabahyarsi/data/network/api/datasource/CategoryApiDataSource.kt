package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetSubCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.service.CategoryService
import retrofit2.http.Path
import javax.inject.Inject


interface CategoryApiDataSource {
    suspend fun getAllCategory(): GetCategoryResponse
    suspend fun getSubCategoryByCategoryId(id: String): GetSubCategoryResponse
}

class CategoryApiDataSourceImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryApiDataSource {
    override suspend fun getAllCategory(): GetCategoryResponse =
        categoryService.getAllCategory()

    override suspend fun getSubCategoryByCategoryId(id: String): GetSubCategoryResponse =
        categoryService.getSubCategoryByCategoryId(id)

}