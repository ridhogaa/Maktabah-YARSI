package com.maktabah.maktabahyarsi.ui.resultsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.datastore.HighlightTextPreferenceDataSource
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
class ResultSearchViewModel @Inject constructor(
    private val pref: HighlightTextPreferenceDataSource
) : ViewModel() {
    fun setHighlightText(text: String) = viewModelScope.launch(Dispatchers.IO) {
        pref.setHighlightTextPref(text)
    }
}