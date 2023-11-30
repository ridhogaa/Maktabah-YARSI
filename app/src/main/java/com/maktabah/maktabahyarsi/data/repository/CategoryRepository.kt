package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.CategoryApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetSubCategoryByIdCategoryResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface CategoryRepository {
    suspend fun getAllCategory(): Flow<ResultWrapper<GetCategoryResponse>>
    suspend fun getSubCategoryByIdCategory(id: String): Flow<ResultWrapper<GetSubCategoryByIdCategoryResponse>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApiDataSource: CategoryApiDataSource
) : CategoryRepository {
    override suspend fun getAllCategory(): Flow<ResultWrapper<GetCategoryResponse>> =
        proceedFlow { categoryApiDataSource.getAllCategory() }

    override suspend fun getSubCategoryByIdCategory(id: String): Flow<ResultWrapper<GetSubCategoryByIdCategoryResponse>> =
        proceedFlow {
            categoryApiDataSource.getSubCategoryByIdCategory(id)
        }

}