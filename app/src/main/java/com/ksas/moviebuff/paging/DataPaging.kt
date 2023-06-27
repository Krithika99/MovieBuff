package com.ksas.moviebuff.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ksas.moviebuff.api.generic.Results
import com.ksas.moviebuff.repository.MoviesRepository


class MovieDataSource(private val moviesRepository: MoviesRepository, private val title: String) :
    PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = moviesRepository.searchMovieByTitle(title, nextPageNumber)
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