package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.CategoryApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetSubCategoryResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface CategoryRepository {
    suspend fun getAllCategory(): Flow<ResultWrapper<GetCategoryResponse>>
    suspend fun getSubCategoryByCategoryId(id: String): Flow<ResultWrapper<GetSubCategoryResponse>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val categoryApiDataSource: CategoryApiDataSource
) : CategoryRepository {
    override suspend fun getAllCategory(): Flow<ResultWrapper<GetCategoryResponse>> =
        proceedFlow { categoryApiDataSource.getAllCategory() }

    override suspend fun getSubCategoryByCategoryId(id: String): Flow<ResultWrapper<GetSubCategoryResponse>> =
        proceedFlow {
            categoryApiDataSource.getSubCategoryByCategoryId(id)
        }

}