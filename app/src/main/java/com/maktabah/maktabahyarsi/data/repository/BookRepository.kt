package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface BookRepository {
    suspend fun getBooks(sort: String? = null): Flow<ResultWrapper<GetBookResponse>>
}

class BookRepositoryImpl @Inject constructor(
    private val bookApiDataSource: BookApiDataSource
) : BookRepository {
    override suspend fun getBooks(sort: String?): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooks(sort)
        }

}