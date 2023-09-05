package com.example.requestapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.requestapi.database.AppDatabase
import com.example.requestapi.model.Movie
import kotlinx.coroutines.launch

class MainViewModel(private val db: AppDatabase) : ViewModel() {
    val favouriteMovies = MutableLiveData<List<Movie>>()

    init {
        viewModelScope.launch {
            favouriteMovies.value = db.favouriteDao().getAllMovies()
        }
    }

    fun addMovieToFavourite(movie: Movie) {
        viewModelScope.launch {
            db.favouriteDao().insertMovie(movie)
            favouriteMovies.value = db.favouriteDao().getAllMovies()
        }
    }
}