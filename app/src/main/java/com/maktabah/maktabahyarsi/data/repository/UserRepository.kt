package com.maktabah.maktabahyarsi.data.repository

import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.datasource.UserApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.utils.getDataJwt
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import com.maktabah.maktabahyarsi.wrapper.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


interface UserRepository {
    suspend fun getUserById(): Flow<ResultWrapper<GetUserByIdResponse>>
}

class UserRepositoryImpl @Inject constructor(
    private val userApiDataSource: UserApiDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : UserRepository {
    override suspend fun getUserById(): Flow<ResultWrapper<GetUserByIdResponse>> =
        proceedFlow {
            userApiDataSource.getUserById(
                "Bearer " + userPreferenceDataSource.getUserTokenPrefFlow().first(),
                userPreferenceDataSource.getUserTokenPrefFlow().first().getDataJwt()
                    .getString("id")
            )
        }

}