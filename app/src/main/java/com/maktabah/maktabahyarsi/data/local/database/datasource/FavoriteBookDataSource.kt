package com.maktabah.maktabahyarsi.data.local.database.datasource

import com.maktabah.maktabahyarsi.data.local.database.dao.FavoriteBookDao
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import javax.inject.Inject

interface FavoriteBookDataSource {
    suspend fun addFavorite(favorite: FavoriteBookEntity)
    suspend fun removeFavorite(favorite: FavoriteBookEntity)
    suspend fun getAllFavorites(idUser: String): List<FavoriteBookEntity>
    suspend fun isFavorite(id: String, idUser: String): Boolean
}

class FavoriteBookDataSourceImpl @Inject constructor(
    private val favoriteBookDao: FavoriteBookDao
) : FavoriteBookDataSource {
    override suspend fun addFavorite(favorite: FavoriteBookEntity) =
        favoriteBookDao.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: FavoriteBookEntity) =
        favoriteBookDao.removeFavorite(favorite)

    override suspend fun getAllFavorites(idUser: String): List<FavoriteBookEntity> =
        favoriteBookDao.getAllFavorites(idUser)

    override suspend fun isFavorite(id: String, idUser: String): Boolean =
        favoriteBookDao.isFavorite(id, idUser)
}