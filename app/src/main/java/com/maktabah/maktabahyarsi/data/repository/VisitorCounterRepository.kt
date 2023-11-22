package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.network.api.datasource.VisitorCounterApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.visitor.GetVisitorCounterResponse
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface VisitorCounterRepository {
    suspend fun getVisitorCounter(): Flow<ResultWrapper<GetVisitorCounterResponse>>

    suspend fun updateVisitorCounter()

}

class VisitorCounterRepositoryImpl @Inject constructor(
    private val visitorCounterApiDataSource: VisitorCounterApiDataSource
) : VisitorCounterRepository {
    override suspend fun getVisitorCounter(): Flow<ResultWrapper<GetVisitorCounterResponse>> =
        proceedFlow {
            visitorCounterApiDataSource.getVisitorCounter()
        }

    override suspend fun updateVisitorCounter() =
        visitorCounterApiDataSource.updateVisitorCounter()

}