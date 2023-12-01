package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetSubCategoryByIdCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.service.CategoryService
import javax.inject.Inject

interface CategoryApiDataSource {
    suspend fun getAllCategory(): GetCategoryResponse
    suspend fun getSubCategoryByIdCategory(id: String): GetSubCategoryByIdCategoryResponse
}

class CategoryApiDataSourceImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryApiDataSource {
    override suspend fun getAllCategory(): GetCategoryResponse = categoryService.getAllCategory()

    override suspend fun getSubCategoryByIdCategory(id: String): GetSubCategoryByIdCategoryResponse =
        categoryService.getSubCategoryByIdCategory(id)

}