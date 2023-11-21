package com.maktabah.maktabahyarsi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maktabah.maktabahyarsi.data.local.database.dao.FavoriteBookDao
import com.maktabah.maktabahyarsi.data.local.database.dao.HistoryBookDao
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity


@Database(
    entities = [FavoriteBookEntity::class, HistoryBookEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteBookDao(): FavoriteBookDao
    abstract fun historyBookDao(): HistoryBookDao
}