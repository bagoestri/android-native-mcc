package com.example.requestapi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    val id : Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Double,
    val original_language: String,
    val original_title: String,
    val popularity: Double,
    val poster_path: String?

)