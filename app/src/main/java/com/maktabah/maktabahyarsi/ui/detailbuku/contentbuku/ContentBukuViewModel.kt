package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentBukuViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _contentDetailResponse =
        MutableStateFlow<ResultWrapper<PagingData<GetContentResponse.Data>>>(ResultWrapper.Empty())
    val contentDetailResponse = _contentDetailResponse.asStateFlow()

    private val _bookResponse =
        MutableStateFlow<ResultWrapper<GetBookByIdResponse>>(ResultWrapper.Loading())
    val bookResponse = _bookResponse.asStateFlow()

    fun getContentDetail(id: String) = viewModelScope.launch(Dispatchers.IO){
        bookRepository.getContents(id).collectLatest {
            _contentDetailResponse.value = it
        }
    }

    fun getBooksById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooksById(id).collectLatest {
            _bookResponse.value = it
        }
    }
}