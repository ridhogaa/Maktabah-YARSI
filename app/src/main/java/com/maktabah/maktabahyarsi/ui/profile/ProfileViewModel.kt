package com.maktabah.maktabahyarsi.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.ThemePreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.data.repository.UserRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val themePreferenceDataSource: ThemePreferenceDataSource
) : ViewModel() {

    private val _userResponse =
        MutableStateFlow<ResultWrapper<GetUserByIdResponse>>(ResultWrapper.Loading())
    val userResponse = _userResponse.asStateFlow()

    fun getUserById() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUserById().collectLatest {
            _userResponse.value = it
        }
    }

    fun removeSession() = viewModelScope.launch(Dispatchers.IO) {
        userPreferenceDataSource.removeIdPref()
        userPreferenceDataSource.removeTokenPref()
    }

    fun setTheme(isDarkMode: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        themePreferenceDataSource.setTheme(isDarkMode)
    }

    val getTheme = themePreferenceDataSource.getTheme().asLiveData(Dispatchers.IO)

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)
}