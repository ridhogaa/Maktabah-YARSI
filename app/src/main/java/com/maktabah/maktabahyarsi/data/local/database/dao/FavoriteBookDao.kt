package com.maktabah.maktabahyarsi.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity

@Dao
interface FavoriteBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteBookEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteBookEntity)

    @Query("SELECT * FROM favorite_book WHERE id_user = :idUser")
    suspend fun getAllFavorites(idUser: String): List<FavoriteBookEntity>

    @Query("SELECT EXISTS(SELECT id_buku FROM favorite_book WHERE id_buku = :id AND id_user = :idUser)")
    suspend fun isFavorite(id: String, idUser: String): Boolean
}