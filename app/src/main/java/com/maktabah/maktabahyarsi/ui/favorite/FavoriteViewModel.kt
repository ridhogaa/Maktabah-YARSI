package com.maktabah.maktabahyarsi.ui.favorite

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _favBookResponse =
        MutableStateFlow<ResultWrapper<List<FavoriteBookEntity>>>(ResultWrapper.Loading())
    val favBookResponse = _favBookResponse.asStateFlow()

    fun getFavoriteBooks() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getAllFavorites(userPreferenceDataSource.getUserIdPrefFlow().first())
            .collectLatest {
                it.payload?.let { list ->
                    if (list.isEmpty()) {
                        _favBookResponse.value = ResultWrapper.Empty()
                    } else {
                        _favBookResponse.value = it
                    }
                }
            }
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

    val getUserTokenPrefFlow =
        userPreferenceDataSource.getUserTokenPrefFlow().asLiveData(Dispatchers.IO)
}