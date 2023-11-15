package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface BookRepository {
    suspend fun getBooksBySort(sort: String? = null): Flow<ResultWrapper<GetBookResponse>>
    suspend fun getBooksById(id: String? = null): Flow<ResultWrapper<GetBookResponse>>
    suspend fun getBooksByCategory(category: String? = null): Flow<ResultWrapper<GetBookResponse>>
}

class BookRepositoryImpl @Inject constructor(
    private val bookApiDataSource: BookApiDataSource
) : BookRepository {
    override suspend fun getBooksBySort(sort: String?): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksBySort(sort)
        }

    override suspend fun getBooksById(id: String?): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksById(id)
        }

    override suspend fun getBooksByCategory(category: String?): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksByCategory(category)
        }

}