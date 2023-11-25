package com.maktabah.maktabahyarsi.ui.riwayat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.database.entity.HistoryBookEntity
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {
    private val _historyBookResponse =
        MutableStateFlow<ResultWrapper<List<HistoryBookEntity>>>(ResultWrapper.Loading())
    val historyBookResponse = _historyBookResponse.asStateFlow()

    fun getHistoryBooks() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getAllHistories(userPreferenceDataSource.getUserIdPrefFlow().first())
            .collectLatest {
                it.payload?.let { list ->
                    if (list.isEmpty()) {
                        _historyBookResponse.value = ResultWrapper.Empty()
                    } else {
                        _historyBookResponse.value = it
                    }
                }
            }
    }

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)
}