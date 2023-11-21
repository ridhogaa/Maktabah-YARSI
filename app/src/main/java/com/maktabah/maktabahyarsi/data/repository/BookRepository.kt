package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.local.database.datasource.FavoriteBookDataSource
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
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
    suspend fun addFavorite(favorite: FavoriteBookEntity)
    suspend fun removeFavorite(favorite: FavoriteBookEntity)
    suspend fun getAllFavorites(idUser: String): Flow<ResultWrapper<List<FavoriteBookEntity>>>
    suspend fun isFavorite(id: String, idUser: String): Boolean
}

class BookRepositoryImpl @Inject constructor(
    private val bookApiDataSource: BookApiDataSource,
    private val favoriteBookDataSource: FavoriteBookDataSource
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

}