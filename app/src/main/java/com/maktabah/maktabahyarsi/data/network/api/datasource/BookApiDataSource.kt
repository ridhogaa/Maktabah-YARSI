package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import javax.inject.Inject


interface BookApiDataSource {
    suspend fun getBooksBySort(sort: String? = null): GetBookResponse
    suspend fun getBooksById(id: String): GetBookByIdResponse
    suspend fun getBooksByCategoryId(id: String): GetBookResponse
}

class BookApiDataSourceImpl @Inject constructor(
    private val bookService: BookService
) : BookApiDataSource {
    override suspend fun getBooksBySort(sort: String?): GetBookResponse =
        bookService.getBooks(sort = sort)

    override suspend fun getBooksById(id: String): GetBookByIdResponse =
        bookService.getBookById(id = id)

    override suspend fun getBooksByCategoryId(id: String): GetBookResponse =
        bookService.getBooksByCategoryId(id)

}