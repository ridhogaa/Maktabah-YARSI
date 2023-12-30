package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import com.maktabah.maktabahyarsi.data.local.datastore.HighlightTextPreferenceDataSource
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookByIdResponse
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.utils.currentDate
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentBukuViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val pref: HighlightTextPreferenceDataSource,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    private val _contentDetailResponse =
        MutableStateFlow<ResultWrapper<PagingData<GetContentResponse.Data>>>(ResultWrapper.Empty())
    val contentDetailResponse = _contentDetailResponse.asStateFlow()

    private val _bookResponse =
        MutableStateFlow<ResultWrapper<GetBookByIdResponse>>(ResultWrapper.Loading())
    val bookResponse = _bookResponse.asStateFlow()

    fun getContentDetail(id: String, page: Int? = null) = viewModelScope.launch(Dispatchers.IO){
        bookRepository.getContents(id, page).collectLatest {
            _contentDetailResponse.value = it
        }
    }

    fun getBooksById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooksById(id).collectLatest {
            _bookResponse.value = it
        }
    }

    val getHighlightText = pref.getHighlightTextPrefFlow().asLiveData(Dispatchers.IO)

    fun removeHighlight() = viewModelScope.launch(Dispatchers.IO) {
        pref.removeHighlightTextPref()
    }

    fun addOrUpdateHistory(
        id: String,
        title: String,
        desc: String,
        page: Int,
        creator: String,
        imageUrl: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.addOrUpdateHistory(
            HistoryBookEntity(
                id,
                title,
                desc,
                page,
                creator,
                imageUrl,
                userPreferenceDataSource.getUserIdPrefFlow().first(),
                currentDate
            )
        )
    }

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)
}