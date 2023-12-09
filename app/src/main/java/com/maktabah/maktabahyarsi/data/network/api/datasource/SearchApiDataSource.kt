package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import com.maktabah.maktabahyarsi.data.network.api.service.SearchService
import javax.inject.Inject

interface SearchApiDataSource {
    suspend fun searchContents(searchRequest: SearchRequest): SearchContentResponse
    suspend fun searchBooks(title: String): GetBookResponse
}

class SearchApiDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchApiDataSource {
    override suspend fun searchContents(searchRequest: SearchRequest): SearchContentResponse =
        searchService.searchContents(searchRequest)

    override suspend fun searchBooks(title: String): GetBookResponse =
        searchService.searchBooks(title)
}