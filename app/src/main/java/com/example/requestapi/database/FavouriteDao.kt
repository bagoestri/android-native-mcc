package com.example.requestapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.requestapi.model.Movie

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<Movie>

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

}