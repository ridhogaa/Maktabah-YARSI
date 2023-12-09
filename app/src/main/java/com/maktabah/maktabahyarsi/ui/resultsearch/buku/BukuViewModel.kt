package com.maktabah.maktabahyarsi.ui.resultsearch.buku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import com.maktabah.maktabahyarsi.data.repository.SearchRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BukuViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _search = MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val search = _search.asStateFlow()

    fun search(q: String) = viewModelScope.launch(Dispatchers.IO) {
        if (q == "") {
            _search.value = ResultWrapper.Empty()
        } else {
            _search.value = ResultWrapper.Loading()
            searchRepository.searchBooks(q).collectLatest {
                if (it.payload?.data?.isEmpty() == true) {
                    _search.value = ResultWrapper.Empty()
                } else {
                    _search.value = it
                }
            }
        }
    }
}