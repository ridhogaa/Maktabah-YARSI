package com.maktabah.maktabahyarsi.data.local.database.datasource

import com.maktabah.maktabahyarsi.data.local.database.dao.HistoryBookDao
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import javax.inject.Inject


interface HistoryBookDataSource {
    suspend fun addHistory(historyBookEntity: HistoryBookEntity)
    suspend fun getAllHistories(idUser: String): List<HistoryBookEntity>
}

class HistoryBookDataSourceImpl @Inject constructor(
    private val historyBookDao: HistoryBookDao
) : HistoryBookDataSource {
    override suspend fun addHistory(historyBookEntity: HistoryBookEntity) =
        historyBookDao.addHistory(historyBookEntity)

    override suspend fun getAllHistories(idUser: String): List<HistoryBookEntity> =
        historyBookDao.getAllHistories(idUser)
}