package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.SearchApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface SearchRepository {
    suspend fun searchContents(searchRequest: SearchRequest): Flow<ResultWrapper<SearchContentResponse>>
    suspend fun searchBooks(title: String): Flow<ResultWrapper<GetBookResponse>>
}

class SearchRepositoryImpl @Inject constructor(
    private val searchApiDataSource: SearchApiDataSource
) : SearchRepository {
    override suspend fun searchContents(searchRequest: SearchRequest): Flow<ResultWrapper<SearchContentResponse>> =
        proceedFlow {
            searchApiDataSource.searchContents(searchRequest)
        }

    override suspend fun searchBooks(title: String): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            searchApiDataSource.searchBooks(title)
        }
}