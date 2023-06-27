package com.ksas.moviebuff.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ksas.moviebuff.api.generic.GenericResult
import com.ksas.moviebuff.repository.MoviesRepository


class UpcomingPaging(private val moviesRepository: MoviesRepository) :
    PagingSource<Int, GenericResult>() {

    private val TAG = "UpcomingPaging"

    override fun getRefreshKey(state: PagingState<Int, GenericResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenericResult> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = moviesRepository.upcomingMovies(nextPageNumber)
            Log.d(TAG, "Upcoming pager response check : ${response.results}")
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.page.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}