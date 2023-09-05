package com.example.requestapi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.requestapi.model.Movie
import com.example.requestapi.model.User

@Database(entities = [User::class, Movie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favouriteDao(): FavouriteDao
}