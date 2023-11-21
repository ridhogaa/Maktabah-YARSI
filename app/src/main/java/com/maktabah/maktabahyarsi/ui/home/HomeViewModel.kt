package com.maktabah.maktabahyarsi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.maktabah.maktabahyarsi.data.local.database.entity.FavoriteBookEntity
import com.maktabah.maktabahyarsi.data.local.datastore.UserPreferenceDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginRequestBody
import com.maktabah.maktabahyarsi.data.network.api.model.auth.LoginResponse
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetBookResponse
import com.maktabah.maktabahyarsi.data.network.api.model.category.GetCategoryResponse
import com.maktabah.maktabahyarsi.data.network.api.model.visitor.GetVisitorCounterResponse
import com.maktabah.maktabahyarsi.data.repository.BookRepository
import com.maktabah.maktabahyarsi.data.repository.CategoryRepository
import com.maktabah.maktabahyarsi.data.repository.VisitorCounterRepository
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
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val bookRepository: BookRepository,
    private val visitorCounterRepository: VisitorCounterRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _categoryResponse =
        MutableStateFlow<ResultWrapper<GetCategoryResponse>>(ResultWrapper.Loading())
    val categoryResponse = _categoryResponse.asStateFlow()

    private val _latestBookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val latestBookResponse = _latestBookResponse.asStateFlow()

    private val _recommendBookResponse =
        MutableStateFlow<ResultWrapper<GetBookResponse>>(ResultWrapper.Loading())
    val recommendBookResponse = _recommendBookResponse.asStateFlow()

    private val _visitorResponse =
        MutableStateFlow<ResultWrapper<GetVisitorCounterResponse>>(ResultWrapper.Loading())
    val visitorResponse = _visitorResponse.asStateFlow()

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getAllCategory() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.getAllCategory().collectLatest {
            _categoryResponse.value = it
        }
    }

    fun getLatestBook() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooksBySort("createdAt").collectLatest {
            _latestBookResponse.value = it
        }
    }

    fun getRecommendedBook() = viewModelScope.launch(Dispatchers.IO) {
        bookRepository.getBooksBySort("total").collectLatest {
            _recommendBookResponse.value = it
        }
    }

    fun getVisitorCounter() = viewModelScope.launch(Dispatchers.IO) {
        visitorCounterRepository.getVisitorCounter("november", "2023").collectLatest {
            _visitorResponse.value = it
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
}