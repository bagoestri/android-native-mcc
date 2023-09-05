package com.example.requestapi.API

import com.example.requestapi.model.Movie
import com.example.requestapi.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWY4OWY2ZmVmZDY1ZWJjYTNmYmYwYWI1ZmU1MDQ5ZiIsInN1YiI6IjY0ZTg1MDNmOTBlYTRiMDEwMWY2Nzc1NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.L2AKLXY8LKnsL-E2ksdG3-g5hIVDhzCdtoR85n5xF3o")
    @GET("3/movie/now_playing")
    fun getNowPlaying(@Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>



    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWY4OWY2ZmVmZDY1ZWJjYTNmYmYwYWI1ZmU1MDQ5ZiIsInN1YiI6IjY0ZTg1MDNmOTBlYTRiMDEwMWY2Nzc1NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.L2AKLXY8LKnsL-E2ksdG3-g5hIVDhzCdtoR85n5xF3o")
    @GET("3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Call<Movie>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWY4OWY2ZmVmZDY1ZWJjYTNmYmYwYWI1ZmU1MDQ5ZiIsInN1YiI6IjY0ZTg1MDNmOTBlYTRiMDEwMWY2Nzc1NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.L2AKLXY8LKnsL-E2ksdG3-g5hIVDhzCdtoR85n5xF3o")
    @GET("3/discover/movie")
    fun getAllMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Call<MovieResponse>


}