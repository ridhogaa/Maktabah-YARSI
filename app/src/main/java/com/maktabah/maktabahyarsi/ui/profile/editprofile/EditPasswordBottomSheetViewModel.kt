package com.maktabah.maktabahyarsi.ui.profile.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdatePasswordRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.user.UpdateUserResponse
import com.maktabah.maktabahyarsi.data.repository.UserRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditPasswordBottomSheetViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _updateResponse =
        MutableStateFlow<ResultWrapper<UpdateUserResponse>>(ResultWrapper.Loading())
    val updateResponse = _updateResponse.asStateFlow()

    fun updatePasswordUserById(updatePasswordRequestBody: UpdatePasswordRequestBody) =
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updatePasswordUserById(updatePasswordRequestBody).collectLatest {
                _updateResponse.value = it
            }
        }
}