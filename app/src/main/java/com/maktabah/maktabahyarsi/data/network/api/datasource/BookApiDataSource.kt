package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import javax.inject.Inject


interface BookApiDataSource {
    suspend fun getBooks(sort: String? = null): GetBookResponse
}

class BookApiDataSourceImpl @Inject constructor(
    private val bookService: BookService
) : BookApiDataSource {
    override suspend fun getBooks(sort: String?): GetBookResponse =
        bookService.getBooks(sort)

}