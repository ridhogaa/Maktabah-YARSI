package com.maktabah.maktabahyarsi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
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
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _categoryResponse =
        MutableStateFlow<ResultWrapper<GetCategoryResponse>>(ResultWrapper.Loading())
    val categoryResponse = _categoryResponse.asStateFlow()

    private val _latestBookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val latestBookResponse = _latestBookResponse.asStateFlow()

    private val _recommendBookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val recommendBookResponse = _recommendBookResponse.asStateFlow()

    fun getAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.getAllCategory().collectLatest {
            _categoryResponse.value = it
        }
    }

    fun getLatestBook() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooks("createdAt").collectLatest {
            _latestBookResponse.value = it
        }
    }

    fun getRecommendedBook() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooks("total").collectLatest {
            _recommendBookResponse.value = it
        }
    }
}