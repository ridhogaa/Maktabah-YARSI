package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import com.maktabah.maktabahyarsi.data.network.api.service.BookService
import retrofit2.http.Path
import javax.inject.Inject


interface BookApiDataSource {
    suspend fun getBooksBySort(sort: String? = null): GetBookResponse
    suspend fun getBooksById(id: String): GetBookByIdResponse
    suspend fun getBooksByCategoryId(id: String): GetBookResponse
    suspend fun getContents(id: String, page: Int): GetContentResponse
    suspend fun updateTotalReadingBook(idBibliography: String)
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

    override suspend fun getContents(id: String, page: Int): GetContentResponse =
        bookService.getContents(id, page)

    override suspend fun updateTotalReadingBook(idBibliography: String) =
        bookService.updateTotalReadingBook(idBibliography)
}