package com.maktabah.maktabahyarsi.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.repository.AuthRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _loginResponse =
        MutableStateFlow<ResultWrapper<LoginResponse>>(ResultWrapper.Loading())
    val loginResponse = _loginResponse.asStateFlow()

    fun login(loginRequestBody: LoginRequestBody) = viewModelScope.launch {
        authRepository.login(loginRequestBody).collectLatest {
            _loginResponse.value = it
        }
    }

    fun setUserTokenPref(token: String) = viewModelScope.launch(Dispatchers.IO) {
        userPreferenceDataSource.setUserTokenPref(token)
    }

    fun setUserIdPref(id: String) = viewModelScope.launch(Dispatchers.IO) {
        userPreferenceDataSource.setUserIdPref(id)
    }
}