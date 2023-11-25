package com.maktabah.maktabahyarsi.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity

@Dao
interface HistoryBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(historyBookEntity: HistoryBookEntity)

    @Query("SELECT * FROM history_book WHERE id_user = :idUser ORDER BY date DESC")
    suspend fun getAllHistories(idUser: String): List<HistoryBookEntity>

    @Query("SELECT EXISTS(SELECT id_buku FROM history_book WHERE id_buku = :id AND id_user = :idUser)")
    suspend fun isHistory(id: String, idUser: String): Boolean

    @Query("UPDATE history_book SET date = :date WHERE id_buku = :id AND id_user = :idUser ")
    suspend fun updateDate(id: String, idUser: String, date: String)
}