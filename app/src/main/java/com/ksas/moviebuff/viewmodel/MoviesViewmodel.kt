package com.ksas.moviebuff.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ksas.moviebuff.api.generic.*
import com.ksas.moviebuff.paging.MovieDataSource
import com.ksas.moviebuff.paging.UpcomingPaging
import com.ksas.moviebuff.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val TAG = "MoviesViewModel"

    private var _titleResponse: MutableLiveData<Responses> = MutableLiveData()
    var getTitleLiveData: LiveData<Responses> = _titleResponse

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingTest: MutableLiveData<Boolean> = MutableLiveData(false)
    var isLoadingTest: LiveData<Boolean> = _isLoadingTest


    private val _upcomingMovies: MutableLiveData<UpcomingMovies> = MutableLiveData()
    val upcomingMovies: LiveData<UpcomingMovies> = _upcomingMovies

    private val _searchMovieIdResponse: MutableLiveData<MovieByIdResponse> = MutableLiveData()
    val searchMovieIdResponse: LiveData<MovieByIdResponse> = _searchMovieIdResponse

    private val _ratingsByIdResponse: MutableLiveData<Ratings> = MutableLiveData()
    val ratingsByIdResponse: LiveData<Ratings> = _ratingsByIdResponse

    private val job = Job()

    init {
        val scope = CoroutineScope(job + Dispatchers.Main)
        scope.launch {
            try {
                _isLoading.value = true
                val response = getMoviesByTitle(1)
                Log.d("TAGV", response.results.toString())
                _titleResponse.value = response
            } catch (e: Exception) {
                Log.e("TAGV", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Get movies titles
     */

    private suspend fun getMoviesByTitle(page: Int): Responses {
        return repository.getMoviesByTitle(page)
    }

    /**
     * Search movies by their title
     */

    fun searchMovieByTitle(title: String): Flow<PagingData<Results>> {
        var moviePager: Flow<PagingData<Results>> =
            Pager(PagingConfig(100)) {
                MovieDataSource(repository, "spider man")
            }.flow.cachedIn(viewModelScope)

        try {
            _isLoading.value = true
            moviePager =
                Pager(PagingConfig(pageSize = 100)) {
                    MovieDataSource(repository, title = title)
                }.flow.cachedIn(viewModelScope)

        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
        } finally {
            _isLoading.value = false
        }

        return moviePager
    }

    /**
     * Search movies by their id
     */

    fun searchMovieById(id: String) {
        var movieById: MovieByIdResponse
        var ratingsById: Ratings
        try {
            viewModelScope.launch {
                _isLoadingTest.value = true
                movieById = repository.searchMovieById(id)
                ratingsById = repository.movieRatingsById(id)
                _searchMovieIdResponse.value = movieById
                _ratingsByIdResponse.value = ratingsById
            }

        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Error message: ${e.message}")
        } finally {
            viewModelScope.launch {
                delay(500)
            }
            _isLoadingTest.value = false
        }
    }

    /**
     * Upcoming movies
     */

    fun upcomingMovies(): Flow<PagingData<GenericResult>> {
        return try {
            _isLoading.value = true
            val upcomingMoviePager = Pager(PagingConfig(pageSize = 100)) {
                UpcomingPaging(repository)
            }.flow.cachedIn(viewModelScope)

            Log.d(
                TAG,
                "Upcoming viewmodel check $upcomingMoviePager"
            )
            _isLoading.value = false
            upcomingMoviePager
        } catch (e: Exception) {
            Log.d(TAG, "Error message upcoming movies ${e.message}")
            _isLoading.value = false
            flowOf(PagingData.empty())
        }
    }

    fun upcomingMoviesWithoutPaging() {
        viewModelScope.launch {
            val upcomingMovies = repository.upcomingMoviesWithoutPaging()
            _upcomingMovies.value = upcomingMovies
            Log.d(TAG, "KTEST- ${upcomingMovies.results}")
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

