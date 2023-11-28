package com.maktabah.maktabahyarsi.ui.home.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.sub.GetSubCategoryResponse
import com.maktabah.maktabahyarsi.data.repository.CategoryRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryResponse =
        MutableStateFlow<ResultWrapper<GetCategoryResponse>>(ResultWrapper.Loading())
    val categoryResponse = _categoryResponse.asStateFlow()

    private val _subCategoryResponse =
        MutableStateFlow<ResultWrapper<GetSubCategoryResponse>>(ResultWrapper.Loading())
    val subCategoryResponse = _subCategoryResponse.asStateFlow()

    fun getAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.getAllCategory().collectLatest {
            _categoryResponse.value = it
        }
    }

    fun getCategoryByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.getCategoryByName(name).collectLatest {
            _subCategoryResponse.value = it
        }
    }
}