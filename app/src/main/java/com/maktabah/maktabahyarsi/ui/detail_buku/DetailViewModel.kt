package com.maktabah.maktabahyarsi.ui.detail_buku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _bookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val bookResponse = _bookResponse.asStateFlow()

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getBooksById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooksById(id).collectLatest {
            _bookResponse.value = it
        }
    }

    fun addFavorite(
        id: String,
        title: String,
        desc: String,
        page: Int,
        imageUrl: String,
        isFavorite: Boolean,
    ) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.addFavorite(
            FavoriteBookEntity(
                id,
                title,
                desc,
                page,
                imageUrl,
                userPreferenceDataSource.getUserIdPrefFlow().first(),
                isFavorite
            )
        )
    }

    fun removeFavorite(
        id: String,
        title: String,
        desc: String,
        page: Int,
        imageUrl: String,
        isFavorite: Boolean,
    ) = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.removeFavorite(
            FavoriteBookEntity(
                id,
                title,
                desc,
                page,
                imageUrl,
                userPreferenceDataSource.getUserIdPrefFlow().first(),
                isFavorite
            )
        )
    }

    fun isFavorite(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _isFavorite.postValue(
            bookRepository.isFavorite(
                id,
                userPreferenceDataSource.getUserIdPrefFlow().first()
            )
        )
    }

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)
}