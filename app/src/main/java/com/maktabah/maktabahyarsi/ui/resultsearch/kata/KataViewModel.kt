package com.maktabah.maktabahyarsi.ui.resultsearch.kata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.HighlightTextPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchContentResponse
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import com.maktabah.maktabahyarsi.data.repository.SearchRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KataViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val pref: HighlightTextPreferenceDataSource
) : ViewModel() {
    private val _search =
        MutableStateFlow<ResultWrapper<SearchContentResponse>>(ResultWrapper.Loading())
    val search = _search.asStateFlow()

    fun search(q: String) = viewModelScope.launch(Dispatchers.IO) {
        if (q == "") {
            _search.value = ResultWrapper.Empty()
        } else {
            _search.value = ResultWrapper.Loading()
            searchRepository.searchContents(
                SearchRequest(
                    "match",
                    "text",
                    q,
                    "contents",
                    true,
                )
            ).collectLatest {
                if (it.payload?.data?.isEmpty() == true){
                    _search.value = ResultWrapper.Empty()
                }else{
                    _search.value = it
                }
            }
        }
    }

    fun setHighlightText(text: String) = viewModelScope.launch(Dispatchers.IO) {
        pref.setHighlightTextPref(text)
    }
}