package com.maktabah.maktabahyarsi.ui.resultsearch.kata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.HighlightTextPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.search.SearchRequest
import com.maktabah.maktabahyarsi.data.network.api.model.search.Source
import com.maktabah.maktabahyarsi.data.repository.SearchRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KataViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val pref: HighlightTextPreferenceDataSource
) : ViewModel() {
    private val _search = MutableStateFlow<ResultWrapper<List<Source>>>(ResultWrapper.Loading())
    val search = _search.asStateFlow()

    fun search(q: String) = viewModelScope.launch(Dispatchers.IO) {
        _search.value = ResultWrapper.Loading()
        if (q == "") {
            _search.value = ResultWrapper.Empty()
        } else {
            searchRepository.searchContents(
                SearchRequest(
                    "match_phrase",
                    "text",
                    q.lowercase(),
                    "contents",
                    true,
                )
            ).flowOn(Dispatchers.IO)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ResultWrapper.Loading()
                ).onEmpty {
                    _search.value = ResultWrapper.Empty()
                }.collect {
                    _search.value = it
                }
        }
    }

    fun setHighlightText(text: String) = viewModelScope.launch(Dispatchers.IO) {
        pref.setHighlightTextPref(text)
    }
}