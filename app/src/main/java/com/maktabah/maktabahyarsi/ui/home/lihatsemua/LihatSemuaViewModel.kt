package com.maktabah.maktabahyarsi.ui.home.lihatsemua

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
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
class LihatSemuaViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _bookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val bookResponse = _bookResponse.asStateFlow()

    fun getBook(sort: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooks(sort).collectLatest {
            _bookResponse.value = it
        }
    }

}