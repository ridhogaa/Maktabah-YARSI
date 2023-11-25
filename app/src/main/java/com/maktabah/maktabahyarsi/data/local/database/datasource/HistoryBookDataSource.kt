package com.maktabah.maktabahyarsi.data.local.database.datasource

import com.maktabah.maktabahyarsi.data.local.database.dao.HistoryBookDao
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import javax.inject.Inject


interface HistoryBookDataSource {
    suspend fun addHistory(historyBookEntity: HistoryBookEntity)
    suspend fun getAllHistories(idUser: String): List<HistoryBookEntity>
    suspend fun isHistory(id: String, idUser: String): Boolean
    suspend fun updateDate(id: String, idUser: String, date: String)
}

class HistoryBookDataSourceImpl @Inject constructor(
    private val historyBookDao: HistoryBookDao
) : HistoryBookDataSource {
    override suspend fun addHistory(historyBookEntity: HistoryBookEntity) =
        historyBookDao.addHistory(historyBookEntity)

    override suspend fun getAllHistories(idUser: String): List<HistoryBookEntity> =
        historyBookDao.getAllHistories(idUser)

    override suspend fun isHistory(id: String, idUser: String): Boolean =
        historyBookDao.isHistory(id, idUser)

    override suspend fun updateDate(id: String, idUser: String, date: String) =
        historyBookDao.updateDate(id, idUser, date)
}