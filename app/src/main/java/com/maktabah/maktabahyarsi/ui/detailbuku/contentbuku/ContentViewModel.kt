package com.maktabah.maktabahyarsi.ui.detailbuku.contentbuku

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maktabah.maktabahyarsi.data.network.api.model.book.ContentData
import com.maktabah.maktabahyarsi.data.network.api.model.book.GetContentResponse
import com.maktabah.maktabahyarsi.data.network.api.service.ContentService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(private val contentService: ContentService) : ViewModel() {
    val contentData = MutableLiveData<GetContentResponse>() // Ubah tipe data
    val errorMessage = MutableLiveData<String>()

    fun getContent(contentId: String) {
        contentService.getContent(contentId).enqueue(object : Callback<GetContentResponse> {

            override fun onResponse(call: Call<GetContentResponse>, response: Response<GetContentResponse>) {
                if (response.isSuccessful) {
                    val content = response.body()
                    if (content != null) {
                        contentData.value = content!!
                    } else {
                        errorMessage.value = "Response body is null."
                    }
                } else {
                    errorMessage.value = "Failed to retrieve content. StatusCode: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<GetContentResponse>, t: Throwable) {
                errorMessage.value = "Network error: ${t.message}"
            }
        })
    }
}