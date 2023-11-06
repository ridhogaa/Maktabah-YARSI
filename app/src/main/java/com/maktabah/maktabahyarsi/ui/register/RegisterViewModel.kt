package com.maktabah.maktabahyarsi.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.repository.AuthRepository
import com.maktabah.maktabahyarsi.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerResponse =
        MutableStateFlow<ResultWrapper<RegisterResponse>>(ResultWrapper.Loading())
    val registerResponse = _registerResponse.asStateFlow()

    fun register(registerRequestBody: RegisterRequestBody) = viewModelScope.launch {
        authRepository.register(registerRequestBody).collectLatest {
            _registerResponse.value = it
        }
    }
}