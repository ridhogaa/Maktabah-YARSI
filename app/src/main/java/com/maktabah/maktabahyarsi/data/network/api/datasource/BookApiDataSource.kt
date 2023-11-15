package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import javax.inject.Inject


interface BookApiDataSource {
    suspend fun getBooksBySort(sort: String? = null): GetBookResponse
    suspend fun getBooksById(id: String? = null): GetBookResponse
    suspend fun getBooksByCategory(category: String? = null): GetBookResponse
}

class BookApiDataSourceImpl @Inject constructor(
    private val bookService: BookService
) : BookApiDataSource {
    override suspend fun getBooksBySort(sort: String?): GetBookResponse =
        bookService.getBooks(sort = sort)

    override suspend fun getBooksById(id: String?): GetBookResponse =
        bookService.getBooks(id = id)

    override suspend fun getBooksByCategory(category: String?): GetBookResponse =
        bookService.getBooks(category = category)

}