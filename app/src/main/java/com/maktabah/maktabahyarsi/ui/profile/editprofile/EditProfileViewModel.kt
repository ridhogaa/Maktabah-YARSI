package com.maktabah.maktabahyarsi.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.user.GetUserByIdResponse
import com.maktabah.maktabahyarsi.data.repository.UserRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _userResponse = MutableStateFlow<ResultWrapper<GetUserByIdResponse>>(ResultWrapper.Loading())
    val userResponse = _userResponse.asStateFlow()

    fun getUserById() = viewModelScope.launch {
        userRepository.getUserById(userPreferenceDataSource.getUserIdPrefFlow().first())
            .collectLatest {
                _userResponse.value = it
            }
    }
}