package com.maktabah.maktabahyarsi.data.network.api.datasource

import com.maktabah.maktabahyarsi.data.network.api.model.visitor.GetVisitorCounterResponse
import com.maktabah.maktabahyarsi.data.network.api.service.VisitorCounterService
import javax.inject.Inject


interface VisitorCounterApiDataSource {
    suspend fun getVisitorCounter(): GetVisitorCounterResponse

    suspend fun updateVisitorCounter()
}

class VisitorCounterApiDataSourceImpl @Inject constructor(
    private val visitorCounterService: VisitorCounterService
) : VisitorCounterApiDataSource {

    override suspend fun getVisitorCounter(): GetVisitorCounterResponse =
        visitorCounterService.getVisitorCounter()

    override suspend fun updateVisitorCounter() =
        visitorCounterService.updateVisitorCounter()

}