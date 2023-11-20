package com.maktabah.maktabahyarsi.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginWithGoogleResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterResponse
import com.maktabah.maktabahyarsi.data.network.api.model.auth.RegisterWithGoogleResponse
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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerResponse =
        MutableStateFlow<ResultWrapper<RegisterResponse>>(ResultWrapper.Loading())
    val registerResponse = _registerResponse.asStateFlow()

    private val _registerGoogleResponse =
        MutableStateFlow<ResultWrapper<RegisterWithGoogleResponse>>(ResultWrapper.Loading())
    val registerGoogleResponse = _registerGoogleResponse.asStateFlow()

    fun register(registerRequestBody: RegisterRequestBody) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.register(registerRequestBody).collectLatest {
            _registerResponse.value = it
        }
    }
    
    fun registerWithGoogle(token: JsonObject) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.registerWithGoogle(token).collectLatest {
            _registerGoogleResponse.value = it
        }
    }
}