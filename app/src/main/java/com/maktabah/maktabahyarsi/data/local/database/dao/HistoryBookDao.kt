package com.maktabah.maktabahyarsi.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity

@Dao
interface HistoryBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(historyBookEntity: HistoryBookEntity)

    @Query("SELECT * FROM history_book WHERE id_user = :idUser")
    suspend fun getAllHistories(idUser: String): List<HistoryBookEntity>
}