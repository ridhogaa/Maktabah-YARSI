package com.maktabah.maktabahyarsi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.maktabah.maktabahyarsi.data.local.datastore.ThemePreferenceDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themePreferenceDataSource: ThemePreferenceDataSource
) : ViewModel() {
    val getTheme = themePreferenceDataSource.getTheme().asLiveData(Dispatchers.IO)
}