package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetListContentBookResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentBukuViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _contentResponse =
        MutableStateFlow<ResultWrapper<GetListContentBookResponse>>(ResultWrapper.Loading())
    val contentResponse = _contentResponse.asStateFlow()

    private val _contentDetailResponse =
        MutableStateFlow<ResultWrapper<GetContentResponse>>(ResultWrapper.Loading())
    val contentDetailResponse = _contentDetailResponse.asStateFlow()

    fun getContentsBook(id: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getContentsBook(id).collectLatest {
            _contentResponse.value = it
        }
    }

    fun getContentDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getContents(id).collectLatest {
            _contentDetailResponse.value = it
        }
    }
}