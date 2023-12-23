package com.maktabah.maktabahyarsi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.maktabah.maktabahyarsi.data.local.database.datasource.FavoriteBookDataSource
import com.maktabah.maktabahyarsi.data.local.database.datasource.HistoryBookDataSource
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.error.BaseErrorResponse
import com.maktabah.maktabahyarsi.data.network.paging.ContentPagingSource
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import retrofit2.http.Path
import javax.inject.Inject


interface BookRepository {
    suspend fun getBooksBySort(sort: String? = null): Flow<ResultWrapper<GetBookResponse>>
    suspend fun getBooksById(id: String): Flow<ResultWrapper<GetBookByIdResponse>>
    suspend fun getBooksByCategory(id: String): Flow<ResultWrapper<GetBookResponse>>
    suspend fun updateTotalReadingBook(idBibliography: String) : Flow<ResultWrapper<Unit>>
    suspend fun getContents(id: String): Flow<ResultWrapper<PagingData<GetContentResponse.Data>>>
    suspend fun addFavorite(favorite: FavoriteBookEntity)
    suspend fun removeFavorite(favorite: FavoriteBookEntity)
    suspend fun getAllFavorites(idUser: String): Flow<ResultWrapper<List<FavoriteBookEntity>>>
    suspend fun isFavorite(id: String, idUser: String): Boolean
    suspend fun addOrUpdateHistory(historyBookEntity: HistoryBookEntity)
    suspend fun getAllHistories(idUser: String): Flow<ResultWrapper<List<HistoryBookEntity>>>
}

class BookRepositoryImpl @Inject constructor(
    private val bookApiDataSource: BookApiDataSource,
    private val favoriteBookDataSource: FavoriteBookDataSource,
    private val historyBookDataSource: HistoryBookDataSource,
) : BookRepository {
    override suspend fun getBooksBySort(sort: String?): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksBySort(sort)
        }

    override suspend fun getBooksById(id: String): Flow<ResultWrapper<GetBookByIdResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksById(id)
        }

    override suspend fun getBooksByCategory(id: String): Flow<ResultWrapper<GetBookResponse>> =
        proceedFlow {
            bookApiDataSource.getBooksByCategoryId(id)
        }

    override suspend fun updateTotalReadingBook(idBibliography: String): Flow<ResultWrapper<Unit>> =
        proceedFlow {
            bookApiDataSource.updateTotalReadingBook(idBibliography)
        }

    override suspend fun getContents(id: String): Flow<ResultWrapper<PagingData<GetContentResponse.Data>>> =
        proceedFlow {
            Pager(
                config = PagingConfig(pageSize = 1, enablePlaceholders = false),
                pagingSourceFactory = { ContentPagingSource(bookApiDataSource, id) }
            ).flow.first()
        }

    override suspend fun addFavorite(favorite: FavoriteBookEntity) =
        favoriteBookDataSource.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: FavoriteBookEntity) =
        favoriteBookDataSource.removeFavorite(favorite)

    override suspend fun getAllFavorites(idUser: String): Flow<ResultWrapper<List<FavoriteBookEntity>>> =
        proceedFlow {
            favoriteBookDataSource.getAllFavorites(idUser).map {
                FavoriteBookEntity(
                    it.id,
                    it.title,
                    it.desc,
                    it.page,
                    it.imageUrl,
                    it.idUser,
                    it.isFavorite
                )
            }
        }

    override suspend fun isFavorite(id: String, idUser: String): Boolean =
        favoriteBookDataSource.isFavorite(id, idUser)

    override suspend fun addOrUpdateHistory(historyBookEntity: HistoryBookEntity) {
        if (historyBookDataSource.isHistory(historyBookEntity.id, historyBookEntity.idUser)) {
            historyBookDataSource.updateDate(
                historyBookEntity.id,
                historyBookEntity.idUser,
                historyBookEntity.date
            )
        } else {
            historyBookDataSource.addHistory(historyBookEntity)
        }
    }

    override suspend fun getAllHistories(idUser: String): Flow<ResultWrapper<List<HistoryBookEntity>>> =
        proceedFlow {
            historyBookDataSource.getAllHistories(idUser).map {
                HistoryBookEntity(
                    it.id,
                    it.title,
                    it.desc,
                    it.page,
                    it.creator,
                    it.imageUrl,
                    it.idUser,
                    it.date
                )
            }
        }
}