package com.maktabah.maktabahyarsi.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maktabah.maktabahyarsi.data.network.api.datasource.BookApiDataSource
import com.maktabah.maktabahyarsi.data.network.api.model.content.GetContentResponse


class ContentPagingSource(
    private val bookApiDataSource: BookApiDataSource,
    private val idBibliography: String,
    private val pageFromSearch: Int? = null
) : PagingSource<Int, GetContentResponse.Data>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, GetContentResponse.Data>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetContentResponse.Data> =
        try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val newPage = params.key ?: pageFromSearch
            val responseData = bookApiDataSource.getContents(
                idBibliography,
                newPage ?: page
            ).data.orEmpty()
            if (newPage != null){
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (newPage == 1) null else newPage - 1,
                    nextKey = if (responseData.isEmpty()) null else newPage + 1
                )
            } else {
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (responseData.isEmpty()) null else page + 1
                )
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
}