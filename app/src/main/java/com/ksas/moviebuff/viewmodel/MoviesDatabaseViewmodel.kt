package com.ksas.moviebuff.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksas.moviebuff.database.Movie
import com.ksas.moviebuff.repository.MoviesDatabaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDatabaseViewModel @Inject constructor(private val repository: MoviesDatabaseImpl) :
    ViewModel() {

    private val _getAllMoviesLivedata: MutableLiveData<List<Movie>> = MutableLiveData()
    val getAllMoviesLivedata: LiveData<List<Movie>> = _getAllMoviesLivedata

    fun insertMovies(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }

    }

    fun getAllMovies(): List<Movie> {
        var movies = emptyList<Movie>()
        viewModelScope.launch {
            movies = repository.getAllMovies()
            _getAllMoviesLivedata.value = movies
        }
        return movies
    }

    fun deleteMovies(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }

    }

}